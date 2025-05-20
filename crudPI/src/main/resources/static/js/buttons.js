
const botoes = document.querySelectorAll("button");
botoes.forEach(botao => {
    botao.addEventListener("click", () => {
        botoes.forEach(b => b.classList.remove("selecionado"));

        // Adiciona a classe ao botão clicado
        botao.classList.add("selecionado");
    });
});

