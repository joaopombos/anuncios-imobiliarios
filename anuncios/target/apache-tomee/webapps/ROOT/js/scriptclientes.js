const API_URL = '/api/clientes';

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('clienteForm');

    carregarClientes();

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

        const cliente = {
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
                body: JSON.stringify(cliente)
            });

            const respostaTexto = await response.text();

            if (!response.ok) {
                console.error(
                    'Erro do servidor:',
                    response.status,
                    respostaTexto
                );

                mostrarMensagem(
                    respostaTexto || 'Erro ao criar cliente',
                    'erro'
                );

                return;
            }

            document.getElementById('clienteForm').reset();
            mostrarMensagem('Cliente adicionado com sucesso!', 'sucesso');

            await carregarClientes();

        } catch (erro) {
            console.error('Erro na comunicação:', erro);
            mostrarMensagem(
                'Não foi possível contactar o servidor',
                'erro'
            );
        }
    }


    async function carregarClientes() {
        const listaDiv = document.getElementById('listaClientes');

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
                    'Erro ao carregar clientes:',
                    response.status,
                    respostaTexto
                );

                mostrarMensagem('Erro ao carregar clientes', 'erro');
                return;
            }

            const clientes = JSON.parse(respostaTexto);

            listaDiv.innerHTML = '';

            if (!Array.isArray(clientes) || clientes.length === 0) {
                listaDiv.innerHTML =
                    '<div class="vazio">Nenhum cliente cadastrado</div>';
                return;
            }

            clientes.forEach(cliente => {
                const clienteDiv = document.createElement('div');
                clienteDiv.className = 'cliente';

                clienteDiv.innerHTML = `
                    <div class="cliente-info">
                        <h3>${cliente.nome}</h3>
                        <p><strong>ID:</strong> ${cliente.id}</p>
                        <p><strong>Contacto:</strong> ${cliente.contacto}</p>
                    </div>
                    <button 
                        type="button" 
                        class="btn btn-danger btn-eliminar"
                        onclick="eliminarCliente(${cliente.id})">
                        Eliminar
                    </button>
                `;


                listaDiv.appendChild(clienteDiv);
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

    async function eliminarCliente(id) {
        if (!confirm('Tem a certeza que quer eliminar este cliente?')) {
            return;
        }

        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                mostrarMensagem('Erro ao eliminar cliente', 'erro');
                return;
            }

            mostrarMensagem('Cliente eliminado com sucesso!', 'sucesso');
            await carregarClientes();

        } catch (error) {
            console.error('Erro ao eliminar:', error);
            mostrarMensagem('Erro de comunicação com o servidor', 'erro');
        }
    }
    window.eliminarCliente = eliminarCliente;


});
