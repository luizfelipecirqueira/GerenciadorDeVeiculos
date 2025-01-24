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
    fetch(`/GerenciadorDeVeiculos/api/veiculos/${id}`)
        .then(response => response.json())
        .then(veiculo => {
            if (!veiculo) {
                alert('Veículo não encontrado.');
                return;
            }

            document.getElementById('formVeiculo').reset();
            document.getElementById('carroCampos').style.display = 'none';
            document.getElementById('motoCampos').style.display = 'none';

            document.getElementById('veiculoId').value = veiculo.id || '';
            document.getElementById('modelo').value = veiculo.modelo || '';
            document.getElementById('fabricante').value = veiculo.fabricante || '';
            document.getElementById('ano').value = veiculo.ano || '';
            document.getElementById('preco').value = veiculo.preco || '';
            document.getElementById('tipoVeiculo').value = veiculo.tipoVeiculo || '';

            if (veiculo.tipoVeiculo === 'carro') {
                document.getElementById('carroCampos').style.display = 'block';
                document.getElementById('quantidadePortas').value = veiculo.quantidadePortas || '';
                document.getElementById('tipoCombustivel').value = veiculo.tipoCombustivel || '';
            } else if (veiculo.tipoVeiculo === 'moto') {
                document.getElementById('motoCampos').style.display = 'block';
                document.getElementById('cilindrada').value = veiculo.cilindrada || '';
            }

            // Exibe o formulário de edição
            document.getElementById('formulario').style.display = 'block'; 
        })
        .catch(error => {
            console.error('Erro ao carregar dados do veículo:', error);
            alert('Erro ao carregar os dados do veículo.');
        });
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

    const veiculoId = document.getElementById('veiculoId').value; // Obtém o ID do veículo
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
    } else if (tipoVeiculo === 'moto') {
        const cilindrada = document.getElementById('cilindrada').value;
        if (!cilindrada) {
            alert('Por favor, preencha todos os campos obrigatórios para moto!');
            return;
        }
        veiculo.tipoVeiculo = 'moto';
        veiculo.cilindrada = cilindrada;
    }

    let method = 'POST';
    let url = '/GerenciadorDeVeiculos/api/veiculos';
    if (veiculoId) {
        method = 'PUT';  // Se tiver ID, será um update
        url = `/GerenciadorDeVeiculos/api/veiculos/${veiculoId}`;
    }

    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(veiculo),
    })
    .then(response => {
        if (response.ok) {
            alert('Veículo salvo com sucesso!');
            loadVeiculos(); 
            cancelarFormulario();
        } else {
            alert('Erro ao salvar o veículo. Tente novamente.');
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
