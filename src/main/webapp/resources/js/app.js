function toggleMenu() {
    document.getElementById('sidebar').classList.toggle('open');
}

function showCatalogTab(tabName) {
    document.querySelectorAll('[data-catalog-panel]').forEach(function (panel) {
        panel.hidden = panel.dataset.catalogPanel !== tabName;
    });
    document.querySelectorAll('[data-catalog-tab]').forEach(function (button) {
        button.classList.toggle('btn-primary', button.dataset.catalogTab === tabName);
        button.classList.toggle('btn-secondary', button.dataset.catalogTab !== tabName);
    });
    window.localStorage.setItem('catalogTab', tabName);
}

document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelector('[data-catalog-panel]')) {
        showCatalogTab(window.localStorage.getItem('catalogTab') || 'libros');
    }
});
