const setLocalStorage = (userId) => {
    window.localStorage.setItem('USER_ID', userId);
}

const getLocalStorage = () => {
    return window.localStorage.getItem('USER_ID');
}

const clearLocalStorage = () => {
    window.localStorage.clear();
}