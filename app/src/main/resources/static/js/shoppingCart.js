

const putOrDeleteInputAddress = () => {
    const inputDeliver = document.getElementById('deliver');

    console.log('input ', inputDeliver);
    console.log(inputDeliver.innerHTML);
    console.log('deliver', inputDeliver.value);

    inputDeliver.value === 'domicilio' ? putInput() : deleteInput();

}

const putInput = () => {
    if (!document.getElementById('domicile') && !document.getElementById('label-domicile')) {
        const inputDeliver = document.getElementById('deliver');
        const labelDomicile = document.createElement('label');
        const br1 = document.createElement('br');
        
        const br2 = document.createElement('br');
        br1.id = 'br1';
        br2.id = 'br2';
        const inputDomicile = document.createElement('input');
        labelDomicile.setAttribute('for', 'domicile');
        labelDomicile.textContent = 'Direccion de Domicilio:';
        labelDomicile.id = 'label-domicile';
        inputDomicile.type = 'text';
        inputDomicile.id = 'domicile';
        inputDomicile.placeholder = 'Dirección de domicilio';
        inputDomicile.value = address;
        inputDomicile.name = 'addressDomicile';
        inputDomicile.setAttribute('required', true);

        inputDeliver.insertAdjacentElement("afterend", br1);
        br1.insertAdjacentElement("afterend", br2);
        br2.insertAdjacentElement("afterend", labelDomicile);
        labelDomicile.insertAdjacentElement("afterend", inputDomicile);
    }
}

const deleteInput = () => {
    if (document.getElementById('domicile') && document.getElementById('label-domicile')) {
        document.getElementById('domicile').remove();
        document.getElementById('label-domicile').remove();
        document.getElementById('br1').remove();
        document.getElementById('br2').remove();
    }
}