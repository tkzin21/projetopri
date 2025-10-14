console.log('Agendaí carregado');

function abrirModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.style.display = "block";
  
    // Aplica tema escuro se o body estiver dark
    if(document.body.classList.contains("dark")) {
      modal.classList.add("dark-modal");
    } else {
      modal.classList.remove("dark-modal");
    }
  }
  