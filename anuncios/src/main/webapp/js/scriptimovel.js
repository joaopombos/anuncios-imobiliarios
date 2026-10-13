const API_URL = '/api/imoveis';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('imovelForm');

    carregarImoveis();
    carregarAgentes();

    form.addEventListener('submit', submeterFormulario);

    async function submeterFormulario(event) {
        event.preventDefault();
        console.log('formulário submetido');

        const moradaInput = document.getElementById('morada');
        const tipologiaInput = document.getElementById('tipologia');
        const metrosInput = document.getElementById('metros');
        const andaresInput = document.getElementById('andares');
        const wcInput = document.getElementById('wc');
        const varandasInput = document.getElementById('varandas');
        const estadoInput = document.getElementById('estado');
        const anoConstrucaoInput = document.getElementById('anoConstrucao');
        const classeEnergeticaInput = document.getElementById('classeEnergetica');
        const tituloAnInput = document.getElementById('tituloAn');
        const descricaoInput = document.getElementById('descricao');
        const estadoAnInput = document.getElementById('estadoAn');
        const precoInput = document.getElementById('preco');
        const agenteIdInput = document.getElementById('agenteId');

        const morada = moradaInput.value.trim();
        const tipologiaTexto = tipologiaInput.value.trim();
        const metrosTexto = metrosInput.value.trim();
        const andaresTexto = andaresInput.value.trim();
        const wcTexto = wcInput.value.trim();
        const varandastexto = varandasInput.value.trim();
        const estado = estadoInput.value.trim();
        const anoConstrucaoTexto = anoConstrucaoInput.value.trim();
        const classeEnergetica = classeEnergeticaInput.value.trim();
        const tituloAn = tituloAnInput.value.trim();
        const descricao = descricaoInput.value.trim();
        const estadoAn = estadoAnInput.value.trim();
        const precoTexto = precoInput.value.trim();
        const agenteId = agenteIdInput.value.trim();
        console.log('Erro: agenteId vazio');

        const tipologia = Number(tipologiaTexto);
        const metros = Number(metrosTexto);
        const andares = Number(andaresTexto);
        const wc = Number(wcTexto);
        const varandas = Number(varandastexto);
        const anoConstrucao = Number(anoConstrucaoTexto);
        const preco = Number(precoTexto);

        if (!morada) {
            mostrarMensagem('Morada é obrigatório', 'erro');
            return;
        }

        if (!tipologia) {
            mostrarMensagem('Tipologia é obrigatório', 'erro');
            return;
        }

        if (!metros) {
            mostrarMensagem('Metros quadrados é obrigatório', 'erro');
            return;
        }

        if (!wc) {
            mostrarMensagem('Número de casas de banho é obrigatório', 'erro');
            return;
        }

        if (!varandas) {
            mostrarMensagem('Número de varandas é obrigatório', 'erro');
            return;
        }

        if (!estado) {
            mostrarMensagem('Estado do imóvel é obrigatório', 'erro');
            return;
        }

        if (!andares) {
            mostrarMensagem('Número de andares é obrigatório', 'erro');
            return;
        }

        if (!tituloAn) {
            mostrarMensagem('Título é obrigatório', 'erro');
            return;
        }

        if (!estadoAn) {
            mostrarMensagem('Estado do anúncio é obrigatório', 'erro');
            return;
        }

        if (!preco) {
            mostrarMensagem('Preço é obrigatório', 'erro');
            return;
        }

        if (!agenteId) {
            console.log('Erro: agenteId vazio');
            mostrarMensagem('Agente responsável é obrigatório', 'erro');
            return;
        }

        const hoje = new Date();
        const dataPubAuto = hoje.toISOString().split('T')[0];

        const imovel = {
            morada: morada,
            tipologia: tipologia,
            metros: metros,
            andares: andares,
            wc: wc,
            varandas: varandas,
            estado: estado,
            anoConstrucao: anoConstrucao,
            classeEnergetica: classeEnergetica,
            tituloAn: tituloAn,
            descricaoAn: descricao,
            dataPub: dataPubAuto,
            estadoAn: estadoAn,
            preco: preco,
            agente: {
                id: parseInt(agenteId)
            }
        };

        console.log('JSON a enviar:', JSON.stringify(imovel));


        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(imovel)
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error('Erro do servidor:', response.status, respostaTexto);
                mostrarMensagem(respostaTexto || 'Erro ao criar imovel', 'erro');
                return;
            }

            document.getElementById('imovelForm').reset();
            mostrarMensagem('Imovél adicionado com sucesso!', 'sucesso');
            await carregarImoveis();

        } catch (erro) {
            console.error('Erro na comunicação:', erro);
            mostrarMensagem('Não foi possível contactar o servidor', 'erro');
        }
    }
    
    function obterClasseEstado(estado) {
    switch (estado) {
        case 'Ativo':
            return 'estado-ativo';

        case 'Desativado':
            return 'estado-desativado';

        case 'Rascunho':
            return 'estado-rascunho';

        default:
            return 'estado-desconhecido';
    }
}


    async function carregarImoveis() {
        const listaDiv = document.getElementById('listaImoveis');

        try {
            const response = await fetch(API_URL, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'
                }
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error('Erro ao carregar imoveis:', response.status, respostaTexto);
                mostrarMensagem('Erro ao carregar imoveis', 'erro');
                return;
            }

            const imoveis = JSON.parse(respostaTexto);
            listaDiv.innerHTML = '';

            if (!Array.isArray(imoveis) || imoveis.length === 0) {
                listaDiv.innerHTML = '<div class="vazio">Nenhum imovel cadastrado</div>';
                return;
            }

            imoveis.forEach(imovel => {
                const imovelDiv = document.createElement('div');
                imovelDiv.className = `imovel ${obterClasseEstado(imovel.estadoAn)}`;

                imovelDiv.innerHTML = `
                    <div class="imovel-info">
                        <h3>${imovel.tituloAn}</h3>
                        <h4>${imovel.preco}€</h4>
                        <p><strong>${imovel.descricaoAn}</strong></p>
                        <p><strong>ID:</strong> ${imovel.id}</p>
                        <p><strong>Tipologia:</strong> T${imovel.tipologia}</p>
                        <p><strong>Metros<sup>2</sup>:</strong> ${imovel.metros}m<sup>2</sup></p>
                        <p><strong>Andares:</strong> ${imovel.andares}</p>
                        <p><strong>Wc:</strong> ${imovel.wc}</p>
                        <p><strong>Varandas:</strong> ${imovel.varandas}</p>
                        <p><strong>Estado:</strong> ${imovel.estado}</p>
                        <p><strong>Ano de construção:</strong> ${imovel.anoConstrucao}</p>
                        <p><strong>Classe energética:</strong> ${imovel.classeEnergetica}</p>
                        <p><strong>Agente responsável:</strong> ${imovel.agente.nome}
                            (contacto: ${imovel.agente.contacto})
                        </p>
                        <p><strong>Anúncio:</strong> ${imovel.estadoAn}</p>
                        <p><strong>Data de Publicação:</strong> ${imovel.dataPub}</p>
                    </div>

                    <div class="imovel-acoes">
                        <button
                            type="button"
                            class="btn btn-primary btn-alterar"
                            onclick="mostrarOpcoesEstado(${imovel.id})">
                            Alterar estado
                        </button>

                        <select
                            id="estado-${imovel.id}"
                            class="select-estado"
                            onchange="confirmarAlteracaoEstado(${imovel.id})"
                            style="display: none;">
                            <option value="">Escolher estado</option>
                            <option value="Ativo" ${imovel.estadoAn === 'Ativo' ? 'selected' : ''}>
                                Ativo
                            </option>
                            <option value="Desativado" ${imovel.estadoAn === 'Desativado' ? 'selected' : ''}>
                                Desativado
                            </option>
                            <option value="Rascunho" ${imovel.estadoAn === 'Rascunho' ? 'selected' : ''}>
                                Rascunho
                            </option>
                        </select>

                        
                        <button 
                            type="button" 
                            class="btn btn-danger btn-eliminar"
                            onclick="eliminarImovel(${imovel.id})">
                            Eliminar
                        </button>

                    </div>
                `;


                listaDiv.appendChild(imovelDiv);
            });

        } catch (erro) {
            console.error('Erro ao interpretar a resposta:', erro);
            mostrarMensagem('Resposta inválida do servidor', 'erro');
        }
    }

    function mostrarMensagem(texto, tipo) {
        const mensagem = document.getElementById('mensagem');
        mensagem.textContent = texto;
        mensagem.className = tipo;

        setTimeout(() => {
            mensagem.className = '';
        }, 5000);
    }

    async function eliminarImovel(id) {
        if (!confirm('Tem a certeza que quer eliminar este imovel?')) {
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                mostrarMensagem('Erro ao eliminar imovel', 'erro');
                return;
            }

            mostrarMensagem('Imovel eliminado com sucesso!', 'sucesso');
            await carregarImoveis();

        } catch (error) {
            console.error('Erro ao eliminar:', error);
            mostrarMensagem('Erro de comunicação com o servidor', 'erro');
        }
    }

    function mostrarOpcoesEstado(id) {
        const selectEstado = document.getElementById(`estado-${id}`);

        if (selectEstado.style.display === 'none') {
            selectEstado.style.display = 'block';
            selectEstado.focus();
        } else {
            selectEstado.style.display = 'none';
            selectEstado.value = '';
        }
    }

    async function confirmarAlteracaoEstado(id) {
        const selectEstado = document.getElementById(`estado-${id}`);
        const novoEstado = selectEstado.value;

        if (!novoEstado) {
            return;
        }

        const confirmou = confirm(
            `Tem a certeza que quer alterar o estado do anúncio para "${novoEstado}"?`
        );

        if (!confirmou) {
            selectEstado.value = '';
            selectEstado.style.display = 'none';
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}/estado`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    estadoAn: novoEstado
                })
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error(
                    'Erro ao alterar estado:',
                    response.status,
                    respostaTexto
                );

                mostrarMensagem(
                    respostaTexto || 'Erro ao alterar o estado do anúncio',
                    'erro'
                );

                return;
            }

            mostrarMensagem(
                'Estado do anúncio alterado com sucesso!',
                'sucesso'
            );

            await carregarImoveis();

        } catch (erro) {
            console.error('Erro de comunicação:', erro);
            mostrarMensagem(
                'Não foi possível contactar o servidor',
                'erro'
            );
        }
    }

    window.mostrarOpcoesEstado = mostrarOpcoesEstado;
    window.confirmarAlteracaoEstado = confirmarAlteracaoEstado;
    window.eliminarImovel = eliminarImovel;

    function carregarAgentes() {
        fetch('/api/agentes')
            .then(response => response.json())
            .then(agentes => {
                const selectAgente = document.getElementById('agenteId');
                selectAgente.innerHTML = '<option value="">Agente responsável</option>';

                agentes.forEach(agente => {
                    const option = document.createElement('option');
                    option.value = agente.id;  // Certifique-se que isto está aqui
                    option.textContent = agente.nome;
                    selectAgente.appendChild(option);
                });

                console.log('Agentes carregados:', agentes); // Debug
            })
            .catch(error => console.error('Erro ao carregar agentes:', error));
    }


});
