// Preenche os números em destaque da home com o total de cada recurso.
document.addEventListener('DOMContentLoaded', () => {
    contar('/api/imoveis', 'numImoveis');
    contar('/api/agentes', 'numAgentes');
    contar('/api/clientes', 'numClientes');
    contar('/api/propostas', 'numPropostas');

    async function contar(url, idElemento) {
        const el = document.getElementById(idElemento);
        try {
            const resp = await fetch(url, { headers: { 'Accept': 'application/json' } });
            const dados = await resp.json();
            if (resp.ok && Array.isArray(dados)) {
                el.textContent = dados.length;
            }
        } catch (e) {
            // resposta não-JSON ou erro: fica "–"
        }
    }
});
