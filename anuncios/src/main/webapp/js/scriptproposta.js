const API_URL = '/api/propostas';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('propostaForm');

    carregarClientes();
    carregarImoveis();
    carregarPropostas();

    form.addEventListener('submit', submeterFormulario);

    async function submeterFormulario(event) {
        event.preventDefault();

        const clienteId = Number(document.getElementById('clienteId').value);
        const imovelId = Number(document.getElementById('imovelId').value);
        const preco = Number(document.getElementById('preco').value.trim());
        const comentario = document.getElementById('comentario').value.trim();

        if (!clienteId) {
            mostrarMensagem('Cliente é obrigatório', 'erro');
            return;
        }

        if (!imovelId) {
            mostrarMensagem('Imóvel é obrigatório', 'erro');
            return;
        }

        if (!Number.isInteger(preco) || preco <= 0) {
            mostrarMensagem('Preço deve ser um número válido', 'erro');
            return;
        }

        if (!comentario) {
            mostrarMensagem('Comentário é obrigatório', 'erro');
            return;
        }

        const proposta = {
            preco: preco,
            comentario: comentario,
            cliente: { id: clienteId },
            imovel: { id: imovelId }
        };

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(proposta)
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error('Erro do servidor:', response.status, respostaTexto);
                mostrarMensagem(respostaTexto || 'Erro ao criar proposta', 'erro');
                return;
            }

            form.reset();
            mostrarMensagem('Proposta adicionada com sucesso!', 'sucesso');
            await carregarPropostas();

        } catch (erro) {
            console.error('Erro na comunicação:', erro);
            mostrarMensagem('Não foi possível contactar o servidor', 'erro');
        }
    }

    async function carregarClientes() {
        const select = document.getElementById('clienteId');
        try {
            const response = await fetch('/api/clientes', { headers: { 'Accept': 'application/json' } });
            if (!response.ok) return;
            const clientes = await response.json();
            clientes.forEach(c => {
                const opt = document.createElement('option');
                opt.value = c.id;
                opt.textContent = `${c.nome} (ID ${c.id})`;
                select.appendChild(opt);
            });
        } catch (erro) {
            console.error('Erro ao carregar clientes:', erro);
        }
    }

    async function carregarImoveis() {
        const select = document.getElementById('imovelId');
        try {
            const response = await fetch('/api/imoveis', { headers: { 'Accept': 'application/json' } });
            if (!response.ok) return;
            const imoveis = await response.json();
            imoveis.forEach(i => {
                const opt = document.createElement('option');
                opt.value = i.id;
                opt.textContent = `${i.tituloAn || i.morada} - ${i.morada} (${i.preco} €)`;
                select.appendChild(opt);
            });
        } catch (erro) {
            console.error('Erro ao carregar imóveis:', erro);
        }
    }

    async function carregarPropostas() {
        const listaDiv = document.getElementById('listaPropostas');

        try {
            const response = await fetch(API_URL, { headers: { 'Accept': 'application/json' } });
            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error('Erro ao carregar propostas:', response.status, respostaTexto);
                mostrarMensagem('Erro ao carregar propostas', 'erro');
                return;
            }

            const propostas = JSON.parse(respostaTexto);
            listaDiv.innerHTML = '';

            if (!Array.isArray(propostas) || propostas.length === 0) {
                listaDiv.innerHTML = '<div class="vazio">Nenhuma proposta registada</div>';
                return;
            }

            propostas.forEach(p => {
                const div = document.createElement('div');
                div.className = 'proposta ' + classeEstado(p.estado);

                const info = document.createElement('div');
                info.className = 'proposta-info';

                const titulo = document.createElement('h3');
                titulo.textContent = p.imovel ? (p.imovel.tituloAn || p.imovel.morada) : 'Imóvel removido';

                const badge = document.createElement('span');
                badge.className = 'badge-estado badge-' + String(p.estado).replace(/\s|ã/g, m => m === 'ã' ? 'a' : '');
                badge.textContent = p.estado;

                info.appendChild(titulo);
                info.appendChild(badge);
                info.appendChild(linha('ID', p.id));
                info.appendChild(linha('Cliente', p.cliente ? p.cliente.nome : '-'));
                info.appendChild(linha('Imóvel', p.imovel ? `${p.imovel.morada} (anúncio: ${p.imovel.preco} €)` : '-'));
                info.appendChild(linha('Preço proposto', `${p.preco} €`));
                info.appendChild(linha('Comentário', p.comentario));

                const acoes = document.createElement('div');
                acoes.className = 'proposta-acoes';

                const btnAlterar = document.createElement('button');
                btnAlterar.type = 'button';
                btnAlterar.className = 'btn btn-primary btn-alterar';
                btnAlterar.textContent = 'Alterar estado';

                const select = document.createElement('select');
                select.className = 'select-estado';
                select.style.display = 'none';
                ['Pendente', 'Aprovado', 'Não Aprovado'].forEach(e => {
                    const opt = document.createElement('option');
                    opt.value = e;
                    opt.textContent = e;
                    opt.selected = (e === p.estado);
                    select.appendChild(opt);
                });

                btnAlterar.addEventListener('click', () => {
                    select.style.display = select.style.display === 'none' ? 'block' : 'none';
                });
                select.addEventListener('change', () => alterarEstado(p.id, select));

                const btn = document.createElement('button');
                btn.type = 'button';
                btn.className = 'btn btn-danger btn-eliminar';
                btn.textContent = 'Eliminar';
                btn.addEventListener('click', () => eliminarProposta(p.id));

                acoes.appendChild(btnAlterar);
                acoes.appendChild(select);
                acoes.appendChild(btn);

                div.appendChild(info);
                div.appendChild(acoes);
                listaDiv.appendChild(div);
            });

        } catch (erro) {
            console.error('Erro ao interpretar a resposta:', erro);
            mostrarMensagem('Resposta inválida do servidor', 'erro');
        }
    }

    function classeEstado(estado) {
        if (estado === 'Aprovado') return 'estado-aprovado';
        if (estado === 'Não Aprovado') return 'estado-naoaprovado';
        if (estado === 'Pendente') return 'estado-pendente';
        return 'estado-desconhecido';
    }

    async function alterarEstado(id, select) {
        const novoEstado = select.value;

        if (!novoEstado) {
            return;
        }

        if (!confirm(`Tem a certeza que quer alterar o estado da proposta para "${novoEstado}"?`)) {
            await carregarPropostas(); // repõe o estado atual no select
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}/estado`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({ estado: novoEstado })
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error('Erro ao alterar estado:', response.status, respostaTexto);
                mostrarMensagem(respostaTexto || 'Erro ao alterar o estado da proposta', 'erro');
                return;
            }

            mostrarMensagem('Estado da proposta alterado com sucesso!', 'sucesso');
            await carregarPropostas();

        } catch (erro) {
            console.error('Erro de comunicação:', erro);
            mostrarMensagem('Não foi possível contactar o servidor', 'erro');
        }
    }

    // <p><strong>rotulo:</strong> valor</p>, sem injetar HTML
    function linha(rotulo, valor) {
        const p = document.createElement('p');
        const strong = document.createElement('strong');
        strong.textContent = rotulo + ': ';
        p.appendChild(strong);
        p.appendChild(document.createTextNode(valor));
        return p;
    }

    function mostrarMensagem(texto, tipo) {
        const mensagem = document.getElementById('mensagem');
        mensagem.textContent = texto;
        mensagem.className = tipo;
        setTimeout(() => { mensagem.className = ''; }, 5000);
    }

    async function eliminarProposta(id) {
        if (!confirm('Tem a certeza que quer eliminar esta proposta?')) {
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });

            if (!response.ok) {
                mostrarMensagem('Erro ao eliminar proposta', 'erro');
                return;
            }

            mostrarMensagem('Proposta eliminada com sucesso!', 'sucesso');
            await carregarPropostas();

        } catch (error) {
            console.error('Erro ao eliminar:', error);
            mostrarMensagem('Erro de comunicação com o servidor', 'erro');
        }
    }
});
