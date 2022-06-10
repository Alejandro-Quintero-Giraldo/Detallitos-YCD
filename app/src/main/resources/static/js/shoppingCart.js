const putOrDeleteInputAddress = () => {
    const inputDeliver = document.getElementById('deliver').value;
    const inputDomicile = document.getElementById('domicile');
    console.log('deliver', inputDeliver);
    inputDeliver === 'domicilio' ? putInput(inputDeliver) : inputDomicile.setAttribute('disabled', true);

}

const putInput = (input) => {
    const inputDomicile = document.createElement('input');
    inputDomicile.type = 'text';
    inputDomicile.id = 'domicile';
    inputDomicile.placeholder = 'Dirección de domicilio';
    inputDomicile.value = '';

    input.insertAdjacentElement("afterend", inputDomicile);
}