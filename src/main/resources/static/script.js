function exchange(){
    const firstCurrency = document.form.firstCurrency.options[0];
    const secondCurrency = document.form.secondCurrency.options[0];
    location.href = '/exchange?firstCurrency=' + firstCurrency + '&secondCurrency=' + secondCurrency;
}