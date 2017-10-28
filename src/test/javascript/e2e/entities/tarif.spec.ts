import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Tarif e2e test', () => {

    let navBarPage: NavBarPage;
    let tarifDialogPage: TarifDialogPage;
    let tarifComponentsPage: TarifComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-doctor.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Tarifs', () => {
        navBarPage.goToEntity('tarif');
        tarifComponentsPage = new TarifComponentsPage();
        expect(tarifComponentsPage.getTitle()).toMatch(/techmedApp.tarif.home.title/);

    });

    it('should load create Tarif dialog', () => {
        tarifComponentsPage.clickOnCreateButton();
        tarifDialogPage = new TarifDialogPage();
        expect(tarifDialogPage.getModalTitle()).toMatch(/techmedApp.tarif.home.createOrEditLabel/);
        tarifDialogPage.close();
    });

    it('should create and save Tarifs', () => {
        tarifComponentsPage.clickOnCreateButton();
        tarifDialogPage.setPrixHTInput('5');
        expect(tarifDialogPage.getPrixHTInput()).toMatch('5');
        tarifDialogPage.setTvaInput('5');
        expect(tarifDialogPage.getTvaInput()).toMatch('5');
        tarifDialogPage.setPrixTTCInput('5');
        expect(tarifDialogPage.getPrixTTCInput()).toMatch('5');
        tarifDialogPage.getActifInput().isSelected().then(function (selected) {
            if (selected) {
                tarifDialogPage.getActifInput().click();
                expect(tarifDialogPage.getActifInput().isSelected()).toBeFalsy();
            } else {
                tarifDialogPage.getActifInput().click();
                expect(tarifDialogPage.getActifInput().isSelected()).toBeTruthy();
            }
        });
        tarifDialogPage.setDateDebutInput(12310020012301);
        expect(tarifDialogPage.getDateDebutInput()).toMatch('2001-12-31T02:30');
        tarifDialogPage.setDateFinInput(12310020012301);
        expect(tarifDialogPage.getDateFinInput()).toMatch('2001-12-31T02:30');
        tarifDialogPage.acteMedicalSelectLastOption();
        tarifDialogPage.save();
        expect(tarifDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class TarifComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-tarif div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TarifDialogPage {
    modalTitle = element(by.css('h4#myTarifLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    prixHTInput = element(by.css('input#field_prixHT'));
    tvaInput = element(by.css('input#field_tva'));
    prixTTCInput = element(by.css('input#field_prixTTC'));
    actifInput = element(by.css('input#field_actif'));
    dateDebutInput = element(by.css('input#field_dateDebut'));
    dateFinInput = element(by.css('input#field_dateFin'));
    acteMedicalSelect = element(by.css('select#field_acteMedical'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setPrixHTInput = function (prixHT) {
        this.prixHTInput.sendKeys(prixHT);
    }

    getPrixHTInput = function () {
        return this.prixHTInput.getAttribute('value');
    }

    setTvaInput = function (tva) {
        this.tvaInput.sendKeys(tva);
    }

    getTvaInput = function () {
        return this.tvaInput.getAttribute('value');
    }

    setPrixTTCInput = function (prixTTC) {
        this.prixTTCInput.sendKeys(prixTTC);
    }

    getPrixTTCInput = function () {
        return this.prixTTCInput.getAttribute('value');
    }

    getActifInput = function () {
        return this.actifInput;
    }
    setDateDebutInput = function (dateDebut) {
        this.dateDebutInput.sendKeys(dateDebut);
    }

    getDateDebutInput = function () {
        return this.dateDebutInput.getAttribute('value');
    }

    setDateFinInput = function (dateFin) {
        this.dateFinInput.sendKeys(dateFin);
    }

    getDateFinInput = function () {
        return this.dateFinInput.getAttribute('value');
    }

    acteMedicalSelectLastOption = function () {
        this.acteMedicalSelect.all(by.tagName('option')).last().click();
    }

    acteMedicalSelectOption = function (option) {
        this.acteMedicalSelect.sendKeys(option);
    }

    getActeMedicalSelect = function () {
        return this.acteMedicalSelect;
    }

    getActeMedicalSelectedOption = function () {
        return this.acteMedicalSelect.element(by.css('option:checked')).getText();
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
