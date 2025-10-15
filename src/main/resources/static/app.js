document.addEventListener("DOMContentLoaded", () => {

    /*** MODAL CONFIGURAÇÕES GLOBAL ***/
    const configIcon = document.getElementById("configIcon");
    const configModal = document.getElementById("configModal");
    const closeConfig = document.getElementById("closeConfig");
    const temaSwitch = document.getElementById("temaSwitch");
  
    if(configIcon && configModal && closeConfig && temaSwitch) {
      configIcon.addEventListener("click", () => configModal.style.display = "block");
      closeConfig.addEventListener("click", () => configModal.style.display = "none");
      window.addEventListener("click", (e) => { if(e.target === configModal) configModal.style.display = "none"; });
  
      // Tema
      if(localStorage.getItem("tema") === "dark") {
        document.body.classList.add("dark");
        temaSwitch.checked = true;
      }
  
      temaSwitch.addEventListener("change", () => {
        if(temaSwitch.checked){
          document.body.classList.add("dark");
          localStorage.setItem("tema", "dark");
        } else {
          document.body.classList.remove("dark");
          localStorage.setItem("tema", "light");
        }
      });
    }
  
    /*** MODAIS EXISTENTES ***/
    function abrirModal(modalId) {
      const modal = document.getElementById(modalId);
      if(!modal) return;
      modal.style.display = "block";
      if(document.body.classList.contains("dark")) modal.classList.add("dark-modal");
      else modal.classList.remove("dark-modal");
    }
  
    function fecharModal(modalId) {
      const modal = document.getElementById(modalId);
      if(!modal) return;
      modal.style.display = "none";
    }
  
    window.addEventListener("click", (e) => {
      document.querySelectorAll(".modal").forEach(modal => { if(e.target === modal) modal.style.display = "none"; });
    });
  
    // Cliente
    const btnAbrirCliente = document.getElementById("abrirModalCliente");
    const btnFecharCliente = document.getElementById("fecharModalCliente");
    if(btnAbrirCliente && btnFecharCliente){
      btnAbrirCliente.addEventListener("click", () => abrirModal("modalCliente"));
      btnFecharCliente.addEventListener("click", () => fecharModal("modalCliente"));
    }
  
    // Profissional
    const btnAbrirProfissional = document.getElementById("abrirModalProfissional");
    const btnFecharProfissional = document.getElementById("fecharModalProfissional");
    if(btnAbrirProfissional && btnFecharProfissional){
      btnAbrirProfissional.addEventListener("click", () => abrirModal("modalProfissional"));
      btnFecharProfissional.addEventListener("click", () => fecharModal("modalProfissional"));
    }
  
    // Agendamento
    const btnAbrirAgendamento = document.getElementById("abrirModalAgendamento");
    const btnFecharAgendamento = document.getElementById("fecharModalAgendamento");
    if(btnAbrirAgendamento && btnFecharAgendamento){
      btnAbrirAgendamento.addEventListener("click", () => abrirModal("modalAgendamento"));
      btnFecharAgendamento.addEventListener("click", () => fecharModal("modalAgendamento"));
    }
  
  });
  