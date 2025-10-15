document.addEventListener("DOMContentLoaded", () => {

    /*** === MODAL CONFIGURAÇÕES GLOBAL === ***/
    const configIcon = document.getElementById("configIcon");
    const configModal = document.getElementById("configModal");
    const closeConfig = document.getElementById("closeConfig");
    const temaSwitch = document.getElementById("temaSwitch");
  
    if(configIcon && configModal && closeConfig && temaSwitch) {
      // Abrir modal
      configIcon.addEventListener("click", () => {
        configModal.style.display = "block";
      });
  
      // Fechar modal
      closeConfig.addEventListener("click", () => {
        configModal.style.display = "none";
      });
  
      // Fechar ao clicar fora
      window.addEventListener("click", (e) => {
        if(e.target === configModal){
          configModal.style.display = "none";
        }
      });
  
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
  
    /*** === MODAIS EXISTENTES DO AGENDAÍ === ***/
  
    // Função genérica para abrir modais
    function abrirModal(modalId) {
      const modal = document.getElementById(modalId);
      if(!modal) return;
  
      modal.style.display = "block";
  
      // Aplica tema escuro se necessário
      if(document.body.classList.contains("dark")) {
        modal.classList.add("dark-modal");
      } else {
        modal.classList.remove("dark-modal");
      }
    }
  
    // Função genérica para fechar modais
    function fecharModal(modalId) {
      const modal = document.getElementById(modalId);
      if(!modal) return;
      modal.style.display = "none";
    }
  
    // Fechar modais ao clicar fora
    window.addEventListener("click", (e) => {
      const modais = document.querySelectorAll(".modal");
      modais.forEach(modal => {
        if(e.target === modal) modal.style.display = "none";
      });
    });
  
    /*** === EXEMPLO: MODAL CLIENTE === */
    const btnAbrirCliente = document.getElementById("abrirModalCliente");
    const btnFecharCliente = document.getElementById("fecharModalCliente");
    if(btnAbrirCliente && btnFecharCliente){
      btnAbrirCliente.addEventListener("click", () => abrirModal("modalCliente"));
      btnFecharCliente.addEventListener("click", () => fecharModal("modalCliente"));
    }
  
    /*** === EXEMPLO: MODAL PROFISSIONAL === */
    const btnAbrirProfissional = document.getElementById("abrirModalProfissional");
    const btnFecharProfissional = document.getElementById("fecharModalProfissional");
    if(btnAbrirProfissional && btnFecharProfissional){
      btnAbrirProfissional.addEventListener("click", () => abrirModal("modalProfissional"));
      btnFecharProfissional.addEventListener("click", () => fecharModal("modalProfissional"));
    }
  
    /*** === EXEMPLO: MODAL AGENDAMENTO === */
    const btnAbrirAgendamento = document.getElementById("abrirModalAgendamento");
    const btnFecharAgendamento = document.getElementById("fecharModalAgendamento");
    if(btnAbrirAgendamento && btnFecharAgendamento){
      btnAbrirAgendamento.addEventListener("click", () => abrirModal("modalAgendamento"));
      btnFecharAgendamento.addEventListener("click", () => fecharModal("modalAgendamento"));
    }
  
  });
  const main = document.getElementById('main');
const modal = document.getElementById('modal');
const gearBtn = document.getElementById('gearBtn');

gearBtn.onclick = () => modal.classList.remove('hidden');
function closeModal() { modal.classList.add('hidden'); }

function showPage(page) {
  switch(page) {
    case 'novo': renderNovoCliente(); break;
    case 'clientes': renderClientes(); break;
    default: renderCalendario();
  }
}

// === FUNÇÕES DAS PÁGINAS ===

// Calendário (pode integrar FullCalendar depois)
function renderCalendario() {
  main.innerHTML = '<h2>Calendário</h2><p>Em breve!</p>';
  XMLHttpRequest
}

// Novo Cliente
function renderNovoCliente() {
  main.innerHTML = `
    <h2>Novo Cliente</h2>
    <form id="clienteForm">
      <input type="text" name="nome" placeholder="Nome" required />
      <input type="text" name="telefone" placeholder="Telefone" required />
      <button type="submit">Salvar</button>
    </form>
  `;

  const form = document.getElementById('clienteForm');
  form.onsubmit = async (e) => {
    e.preventDefault();
    const data = {
      nome: form.nome.value,
      telefone: form.telefone.value
    };
    const res = await fetch('/api/clientes', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if(res.ok) alert('Cliente salvo!');
    form.reset();
    showPage('clientes');
  };
}

// Lista de Clientes
async function renderClientes() {
  main.innerHTML = '<h2>Clientes</h2><ul id="clienteList"></ul>';
  const listEl = document.getElementById('clienteList');
  const res = await fetch('/api/clientes');
  const clientes = await res.json();
  clientes.forEach(c => {
    const li = document.createElement('li');
    li.textContent = `${c.nome} - ${c.telefone}`;
    listEl.appendChild(li);
  });
}

// Inicializa com Calendário
showPage('calendario');
