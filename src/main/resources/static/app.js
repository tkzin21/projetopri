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
<<<<<<< HEAD
  function resetarConfiguracoes() {
    // Apaga só as configs, sem apagar eventos
    localStorage.removeItem('agendaai_tema');
    localStorage.removeItem('agendaai_darkmode');
    localStorage.removeItem('agendaai_view');
    localStorage.removeItem('agendaai_config');
    localStorage.removeItem('agendaai_user');
  
    // Se tiver mais configurações salvas, coloca aqui.
  
    // Recarrega para aplicar o reset
    location.reload();
  }
  document.addEventListener("DOMContentLoaded", function () {
    const btnReset = document.getElementById("resetarTudo");
  
    if (btnReset) {
      btnReset.addEventListener("click", function () {
        console.log("Botão reset detectado!");
  
        // apaga as configs
        localStorage.clear();
  
        // recarrega a página
        location.reload();
      });
    }
  });
  function resetarConfiguracoesSelecionadas() {
    console.log("Resetando apenas configs específicas...");
  
    // Lista das chaves que você quer resetar
    const chavesConfigs = [
      "agendaai_tema",
      "agendaai_darkmode",
      "agendaai_view",
      "agendaai_config"
    ];
  
    // Apaga cada uma
    chavesConfigs.forEach(chave => localStorage.removeItem(chave));
  
    // Recarrega a página para aplicar as alterações
    location.reload();
  }
  function resetarConfiguracoesComConfirmacao() {
    // Mensagem de confirmação
    const confirmacao = confirm("Tem certeza que deseja resetar todas as configurações? Isso não afetará seus eventos.");
    
    if (!confirmacao) return; // Sai se o usuário cancelar
  
    console.log("Resetando apenas configs específicas...");
  
    // Lista das chaves que você quer resetar
    const chavesConfigs = [
      "agendaai_tema",
      "agendaai_darkmode",
      "agendaai_view",
      "agendaai_config"
    ];
  
    // Apaga cada uma
    chavesConfigs.forEach(chave => localStorage.removeItem(chave));
  
    // Recarrega a página para aplicar as alterações
    location.reload();
  }
  function apagarAgendamento(id) {
    if (!confirm("Tem certeza que deseja apagar este agendamento?")) return;
  
    // pega todos os agendamentos
    let agendamentos = JSON.parse(localStorage.getItem("agendaai_agendamentos") || "[]");
  
    // filtra o agendamento que não queremos manter
    agendamentos = agendamentos.filter(a => a.id !== id);
  
    // salva de volta
    localStorage.setItem("agendaai_agendamentos", JSON.stringify(agendamentos));
  
    // atualiza a tela (pode ser recarregar ou re-renderizar)
    location.reload();
  }
=======
  
  
>>>>>>> 18bf45771d8ae1d12ddd47d770e4decc04366f8c
