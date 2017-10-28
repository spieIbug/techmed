import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('ActeMedical e2e test', () => {

    let navBarPage: NavBarPage;
    let acteMedicalDialogPage: ActeMedicalDialogPage;
    let acteMedicalComponentsPage: ActeMedicalComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-doctor.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load ActeMedicals', () => {
        navBarPage.goToEntity('acte-medical');
        acteMedicalComponentsPage = new ActeMedicalComponentsPage();
        expect(acteMedicalComponentsPage.getTitle()).toMatch(/techmedApp.acteMedical.home.title/);

    });

    it('should load create ActeMedical dialog', () => {
        acteMedicalComponentsPage.clickOnCreateButton();
        acteMedicalDialogPage = new ActeMedicalDialogPage();
        expect(acteMedicalDialogPage.getModalTitle()).toMatch(/techmedApp.acteMedical.home.createOrEditLabel/);
        acteMedicalDialogPage.close();
    });

    it('should create and save ActeMedicals', () => {
        acteMedicalComponentsPage.clickOnCreateButton();
        acteMedicalDialogPage.setCodeInput('code');
        expect(acteMedicalDialogPage.getCodeInput()).toMatch('code');
        acteMedicalDialogPage.setLibelleInput('libelle');
        expect(acteMedicalDialogPage.getLibelleInput()).toMatch('libelle');
        acteMedicalDialogPage.codeCCAMSelectLastOption();
        acteMedicalDialogPage.save();
        expect(acteMedicalDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ActeMedicalComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-acte-medical div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ActeMedicalDialogPage {
    modalTitle = element(by.css('h4#myActeMedicalLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    libelleInput = element(by.css('input#field_libelle'));
    codeCCAMSelect = element(by.css('select#field_codeCCAM'));

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

    codeCCAMSelectLastOption = function () {
        this.codeCCAMSelect.all(by.tagName('option')).last().click();
    }

    codeCCAMSelectOption = function (option) {
        this.codeCCAMSelect.sendKeys(option);
    }

    getCodeCCAMSelect = function () {
        return this.codeCCAMSelect;
    }

    getCodeCCAMSelectedOption = function () {
        return this.codeCCAMSelect.element(by.css('option:checked')).getText();
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
