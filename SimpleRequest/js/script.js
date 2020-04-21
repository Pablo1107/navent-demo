const form = document.getElementById('form')

form.addEventListener('submit', (e) => {
  e.preventDefault()
  const formData = new FormData();
  for (let node of e.target.children) {
    if (
      node.nodeName === 'H1' ||
      node.className.includes('buttons')
    ) continue
    const input = node.querySelector('input')
    formData.append(input.name, input.value)
  }
  const xhr = new XMLHttpRequest();
  xhr.open("POST", "http://localhost:8081/pedidos/guardar");
  xhr.send(formData);
})

const name = document.querySelector('input[name="name"]')
const nameError = name.parentNode.parentNode.querySelector('p.help')
name.addEventListener('input', function (event) {
  if (name.value.length > 100) {
    nameError.classList.remove('invisible')
  } else {
    nameError.classList.add('invisible')
  }
});

const amount = document.querySelector('input[name="amount"]')
const amountError = amount.parentNode.parentNode.querySelector('p.help')
amount.addEventListener('input', function (event) {
  if (isNaN(amount.value)) {
    amountError.classList.remove('invisible')
  } else {
    amountError.classList.add('invisible')
  }
});

const discount = document.querySelector('input[name="discount"]')
const discountError = discount.parentNode.parentNode.querySelector('p.help')
discount.addEventListener('input', function (event) {
  if (isNaN(discount.value)) {
    discountError.classList.remove('invisible')
  } else {
    discountError.classList.add('invisible')
  }
});
