import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('RegimeSecuriteSociale e2e test', () => {

    let navBarPage: NavBarPage;
    let regimeSecuriteSocialeDialogPage: RegimeSecuriteSocialeDialogPage;
    let regimeSecuriteSocialeComponentsPage: RegimeSecuriteSocialeComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load RegimeSecuriteSociales', () => {
        navBarPage.goToEntity('regime-securite-sociale');
        regimeSecuriteSocialeComponentsPage = new RegimeSecuriteSocialeComponentsPage();
        expect(regimeSecuriteSocialeComponentsPage.getTitle()).toMatch(/techmedApp.regimeSecuriteSociale.home.title/);

    });

    it('should load create RegimeSecuriteSociale dialog', () => {
        regimeSecuriteSocialeComponentsPage.clickOnCreateButton();
        regimeSecuriteSocialeDialogPage = new RegimeSecuriteSocialeDialogPage();
        expect(regimeSecuriteSocialeDialogPage.getModalTitle()).toMatch(/techmedApp.regimeSecuriteSociale.home.createOrEditLabel/);
        regimeSecuriteSocialeDialogPage.close();
    });

    it('should create and save RegimeSecuriteSociales', () => {
        regimeSecuriteSocialeComponentsPage.clickOnCreateButton();
        regimeSecuriteSocialeDialogPage.setCodeInput('code');
        expect(regimeSecuriteSocialeDialogPage.getCodeInput()).toMatch('code');
        regimeSecuriteSocialeDialogPage.setLibelleInput('libelle');
        expect(regimeSecuriteSocialeDialogPage.getLibelleInput()).toMatch('libelle');
        regimeSecuriteSocialeDialogPage.save();
        expect(regimeSecuriteSocialeDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class RegimeSecuriteSocialeComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-regime-securite-sociale div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RegimeSecuriteSocialeDialogPage {
    modalTitle = element(by.css('h4#myRegimeSecuriteSocialeLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    libelleInput = element(by.css('input#field_libelle'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function (code) {
        this.codeInput.sendKeys(code);
    }

    getCodeInput = function () {
        return this.codeInput.getAttribute('value');
    }

    setLibelleInput = function (libelle) {
        this.libelleInput.sendKeys(libelle);
    }

    getLibelleInput = function () {
        return this.libelleInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
