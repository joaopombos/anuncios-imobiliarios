// Mostra no menu o utilizador autenticado (ou "Entrar") em todas as páginas.
(function () {
    document.addEventListener('DOMContentLoaded', async () => {
        const item = document.getElementById('navAuth');
        if (!item) return;

        try {
            const resp = await fetch('/api/auth/me', { headers: { 'Accept': 'application/json' } });
            if (!resp.ok) return; // sem sessão: fica "Entrar"

            const u = await resp.json();
            const papel = u.tipo === 'AGENTE' ? 'Agente' : 'Cliente';

            item.innerHTML = '';
            const nome = document.createElement('span');
            nome.className = 'nav-link';
            nome.textContent = `${u.nome} (${papel})`;

            const sair = document.createElement('a');
            sair.className = 'nav-link';
            sair.href = '#';
            sair.textContent = 'Sair';
            sair.addEventListener('click', async (e) => {
                e.preventDefault();
                await fetch('/api/auth/logout', { method: 'POST' });
                window.location.href = 'login.html';
            });

            item.className = 'nav-item d-flex align-items-center';
            item.appendChild(nome);
            item.appendChild(sair);
        } catch (e) {
            console.error('Não foi possível verificar a sessão', e);
        }
    });
})();
