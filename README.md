# Anúncios Imobiliários

# Description
  Gestão de anúncios imobiliários, visitas a imóveis, orçamentos, etc...

## Main Entities (Data Base)
  - Agência Imobiliária
  - Agente Imobiliário
  - Cliente
  - Imóvel
  - Anúncio
  - Proposta
  - Visita
  - Agenda

## Main Business Rules
  - O sistema deve permitir ao gestor criar novos imóveis com campos obrigatórios e opcionais.
  – O sistema deve permitir ao gestor editar dados de um imóvel existente.
  – O sistema deve permitir associar múltiplas fotos ao imóvel (upload, descrição e ordenação opcional).
  – O sistema deve permitir definir estado do anúncio (ex.: rascunho, publicado, indisponível).
  – O sistema deve exibir uma página de detalhe com todas as características, fotos e informação relevante sobre o imóvel.
  – O sistema deve permitir filtrar e/ou pesquisar imóveis por parâmetros definidos (ex.: localidade, preço, tipologia, m²).
  – O sistema deve permitir ordenar resultados (ex.: menor preço, maior preço, mais recentes).
  – O sistema deve permitir que um utilizador submeta um pedido de visita para um imóvel.
  – O sistema deve permitir ao utilizador selecionar uma data e horário proposto para a visita.
  – O sistema deve permitir ao gestor visualizar e gerir visitas (cancelar, re-agendar, atualizar estado)
  – O sistema deve permitir ao utilizador submeter uma proposta relacionada com um imóvel.
  – A proposta deve conter, pelo menos: valor proposto, email/contacto e mensagem (campos adicionais opcionais)
  – O sistema deve permitir estados para as propostas (ex.: enviada, em análise, aceite, rejeitada).
  – O gestor deve poder ver as propostas associadas a um imóvel.