const API_URL = '/api/agentes';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('agenteForm');

    carregarAgentes();

    form.addEventListener('submit', submeterFormulario);

    async function submeterFormulario(event) {
        event.preventDefault();

        const nomeInput = document.getElementById('nome');
        const contactoInput = document.getElementById('contacto');

        const nome = nomeInput.value.trim();
        const contactoTexto = contactoInput.value.trim();
        const contacto = Number(contactoTexto);

        if (!nome) {
            mostrarMensagem('Nome é obrigatório', 'erro');
            return;
        }

        if (!contactoTexto || !Number.isInteger(contacto) || contacto <= 0) {
            mostrarMensagem('Contacto deve ser um número válido', 'erro');
            return;
        }

        const agente = {
            nome: nome,
            contacto: contacto
        };

        try {
            const response = await fetch(API_URL, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(agente)
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error(
                    'Erro do servidor:',
                    response.status,
                    respostaTexto
                );

                mostrarMensagem(
                    respostaTexto || 'Erro ao criar agente',
                    'erro'
                );

                return;
            }

            document.getElementById('agenteForm').reset();
            mostrarMensagem('Agente adicionado com sucesso!', 'sucesso');

            await carregarAgentes();

        } catch (erro) {
            console.error('Erro na comunicação:', erro);
            mostrarMensagem(
                'Não foi possível contactar o servidor',
                'erro'
            );
        }
    }


    async function carregarAgentes() {
        const listaDiv = document.getElementById('listaAgentes');

        try {
            const response = await fetch(API_URL, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'
                }
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error(
                    'Erro ao carregar agentes:',
                    response.status,
                    respostaTexto
                );

                mostrarMensagem('Erro ao carregar agentes', 'erro');
                return;
            }

            const agentes = JSON.parse(respostaTexto);

            listaDiv.innerHTML = '';

            if (!Array.isArray(agentes) || agentes.length === 0) {
                listaDiv.innerHTML =
                    '<div class="vazio">Nenhum agente cadastrado</div>';
                return;
            }

            agentes.forEach(agente => {
                const agenteDiv = document.createElement('div');
                agenteDiv.className = 'agente';

                agenteDiv.innerHTML = `
                    <div class="agente-info">
                        <h3>${agente.nome}</h3>
                        <p><strong>ID:</strong> ${agente.id}</p>
                        <p><strong>Contacto:</strong> ${agente.contacto}</p>
                    </div>
                    <button 
                    type="button" 
                    class="btn btn-danger btn-eliminar"
                    onclick="eliminarAgente(${agente.id})">
                    Eliminar
                </button>
                `;


                listaDiv.appendChild(agenteDiv);
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

    async function eliminarAgente(id) {
        if (!confirm('Tem a certeza que quer eliminar este agente?')) {
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                mostrarMensagem('Erro ao eliminar agente', 'erro');
                return;
            }

            mostrarMensagem('Agente eliminado com sucesso!', 'sucesso');
            await carregarAgentes();

        } catch (error) {
            console.error('Erro ao eliminar:', error);
            mostrarMensagem('Erro de comunicação com o servidor', 'erro');
        }
    }
    window.eliminarAgente = eliminarAgente;


});
