function loadVeiculos() {
    fetch('/GerenciadorDeVeiculos/api/veiculos') // URL para o backend
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao carregar os veículos: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const tableBody = document.querySelector('#veiculoTable tbody');
            tableBody.innerHTML = ''; 
            data.forEach(veiculo => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${veiculo.id}</td>
                    <td>${veiculo.modelo}</td>
                    <td>${veiculo.ano}</td>
                    <td>${veiculo.preco}</td>
                    <td>
                        <button onclick="editarVeiculo(${veiculo.id})">Editar</button>
                        <button onclick="detalharVeiculo(${veiculo.id})">Detalhes</button>
                        <button onclick="excluirVeiculo(${veiculo.id})">Excluir</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error(error);
            alert('Ocorreu um erro ao carregar os veículos.');
        });
}


function editarVeiculo(id) {
    console.log('Editar veículo:', id);
    
    window.location.href = `/editarVeiculo?id=${id}`;
}

function detalharVeiculo(id) {
    console.log('Detalhar veículo:', id);

    window.location.href = `/detalhesVeiculo?id=${id}`;
}

function excluirVeiculo(id) {
    if (confirm('Tem certeza que deseja excluir este veículo?')) {
        fetch(`/GerenciadorDeVeiculos/api/veiculos/${id}`, { 
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        })
        .then(response => {
            if (response.ok) {
                alert('Veículo excluído com sucesso!');
                loadVeiculos(); 
            } else {
                alert('Erro ao excluir o veículo. Tente novamente.');
            }
        })
        .catch(error => {
            console.error('Erro ao excluir veículo:', error);
            alert('Ocorreu um erro ao excluir o veículo.');
        });
    }
}


function salvarVeiculo(event) {
    event.preventDefault();

    const modelo = document.getElementById('modelo').value;
    const fabricante = document.getElementById('fabricante').value;
    const ano = document.getElementById('ano').value;
    const preco = document.getElementById('preco').value;
    const tipoVeiculo = document.getElementById('tipoVeiculo').value;

    if (!modelo || !fabricante || !ano || !preco || !tipoVeiculo) {
        alert('Por favor, preencha todos os campos obrigatórios!');
        return;
    }

  
    let veiculo = {
        modelo: modelo,
        fabricante: fabricante,
        ano: ano,
        preco: preco,
    };

    if (tipoVeiculo === 'carro') {
        const quantidadePortas = document.getElementById('quantidadePortas').value;
        const tipoCombustivel = document.getElementById('tipoCombustivel').value;
        if (!quantidadePortas || !tipoCombustivel) {
            alert('Por favor, preencha todos os campos obrigatórios para carro!');
            return;
        }
        veiculo.tipoVeiculo = 'carro';
        veiculo.quantidadePortas = quantidadePortas;
        veiculo.tipoCombustivel = tipoCombustivel;
    }
    
    else if (tipoVeiculo === 'moto') {
        const cilindrada = document.getElementById('cilindrada').value;
        if (!cilindrada) {
            alert('Por favor, preencha todos os campos obrigatórios para moto!');
            return;
        }
        veiculo.tipoVeiculo = 'moto';
        veiculo.cilindrada = cilindrada;
    }

    fetch('/GerenciadorDeVeiculos/api/veiculos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(veiculo),
    })
    .then(response => {
        if (response.ok) {
            alert('Veículo cadastrado com sucesso!');
            loadVeiculos(); 
            cancelarFormulario();
        } else {
            alert('Erro ao cadastrar o veículo. Tente novamente.');
        }
    })
    .catch(error => {
        console.error('Erro ao salvar veículo:', error);
        alert('Ocorreu um erro ao salvar o veículo.');
    });
}

function cancelarFormulario() {
    document.getElementById('formulario').style.display = 'none';
}

function mostrarFormulario() {
    document.getElementById('formulario').style.display = 'block';
}


function mostrarCamposAdicionais() {
    const tipoVeiculo = document.getElementById('tipoVeiculo').value;
    const carroCampos = document.getElementById('carroCampos');
    const motoCampos = document.getElementById('motoCampos');
    const quantidadePortas = document.getElementById('quantidadePortas');
    const tipoCombustivel = document.getElementById('tipoCombustivel');
    const cilindrada = document.getElementById('cilindrada');

 
    carroCampos.style.display = 'none';
    motoCampos.style.display = 'none';

   
    quantidadePortas.removeAttribute('required');
    tipoCombustivel.removeAttribute('required');
    cilindrada.removeAttribute('required');

    if (tipoVeiculo === 'carro') {
        carroCampos.style.display = 'block';
        quantidadePortas.setAttribute('required', 'true');
        tipoCombustivel.setAttribute('required', 'true');
    } else if (tipoVeiculo === 'moto') {
        motoCampos.style.display = 'block';
        cilindrada.setAttribute('required', 'true');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    loadVeiculos(); 
});
