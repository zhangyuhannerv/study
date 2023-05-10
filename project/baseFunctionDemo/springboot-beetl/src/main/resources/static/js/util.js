const util = {
    getContextPath() {
        let pathName = document.location.pathname;

        let index = pathName.substr(1).indexOf("/");

        return pathName.substr(0, index + 1);
    }
}