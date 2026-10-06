document.addEventListener('DOMContentLoaded', () => {
    let tipo = 'CLIENTE';
    let modoRegisto = false;

    const form = document.getElementById('authForm');
    const titulo = document.getElementById('titulo');
    const submitBtn = document.getElementById('submitBtn');
    const trocaModo = document.getElementById('trocaModo');
    const trocaTexto = document.getElementById('trocaTexto');
    const camposRegisto = document.getElementById('camposRegisto');
    const mensagem = document.getElementById('mensagem');

    document.querySelectorAll('#tipoGrupo button').forEach(btn => {
        btn.addEventListener('click', () => {
            tipo = btn.dataset.tipo;
            document.querySelectorAll('#tipoGrupo button')
                .forEach(b => b.classList.toggle('active', b === btn));
        });
    });

    trocaModo.addEventListener('click', (e) => {
        e.preventDefault();
        modoRegisto = !modoRegisto;
        titulo.textContent = modoRegisto ? 'Criar conta' : 'Entrar';
        submitBtn.textContent = modoRegisto ? 'Criar conta' : 'Entrar';
        trocaTexto.textContent = modoRegisto ? 'Já tem conta?' : 'Ainda não tem conta?';
        trocaModo.textContent = modoRegisto ? 'Entrar' : 'Criar conta';
        camposRegisto.style.display = modoRegisto ? 'block' : 'none';
        document.getElementById('password').autocomplete =
            modoRegisto ? 'new-password' : 'current-password';
        mensagem.className = '';
        mensagem.textContent = '';
    });

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        const corpo = { tipo, email, password };

        if (!email || !password) {
            return mostrar('Preencha o email e a palavra-passe', 'erro');
        }

        if (modoRegisto) {
            const nome = document.getElementById('nome').value.trim();
            const contacto = Number(document.getElementById('contacto').value.trim());
            if (!nome) return mostrar('Nome é obrigatório', 'erro');
            if (!Number.isInteger(contacto) || contacto <= 0) {
                return mostrar('Contacto deve ser um número válido', 'erro');
            }
            corpo.nome = nome;
            corpo.contacto = contacto;
        }

        try {
            const resp = await fetch(modoRegisto ? '/api/auth/registar' : '/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
                body: JSON.stringify(corpo)
            });

            if (!resp.ok) {
                let msg = 'Erro ao autenticar';
                try { msg = (await resp.json()).erro || msg; } catch (_) { }
                return mostrar(msg, 'erro');
            }

            mostrar('Sessão iniciada com sucesso!', 'sucesso');
            setTimeout(() => { window.location.href = 'index.html'; }, 600);
        } catch (erro) {
            console.error(erro);
            mostrar('Não foi possível contactar o servidor', 'erro');
        }
    });

    function mostrar(texto, classe) {
        mensagem.textContent = texto;
        mensagem.className = classe;
    }
});
