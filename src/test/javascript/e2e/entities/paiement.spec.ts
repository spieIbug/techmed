import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Paiement e2e test', () => {

    let navBarPage: NavBarPage;
    let paiementDialogPage: PaiementDialogPage;
    let paiementComponentsPage: PaiementComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Paiements', () => {
        navBarPage.goToEntity('paiement');
        paiementComponentsPage = new PaiementComponentsPage();
        expect(paiementComponentsPage.getTitle()).toMatch(/techmedApp.paiement.home.title/);

    });

    it('should load create Paiement dialog', () => {
        paiementComponentsPage.clickOnCreateButton();
        paiementDialogPage = new PaiementDialogPage();
        expect(paiementDialogPage.getModalTitle()).toMatch(/techmedApp.paiement.home.createOrEditLabel/);
        paiementDialogPage.close();
    });

    it('should create and save Paiements', () => {
        paiementComponentsPage.clickOnCreateButton();
        paiementDialogPage.setDateTransationInput(12310020012301);
        expect(paiementDialogPage.getDateTransationInput()).toMatch('2001-12-31T02:30');
        paiementDialogPage.setMontantTTCInput('5');
        expect(paiementDialogPage.getMontantTTCInput()).toMatch('5');
        paiementDialogPage.consultationSelectLastOption();
        paiementDialogPage.moyenSelectLastOption();
        paiementDialogPage.save();
        expect(paiementDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PaiementComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-paiement div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PaiementDialogPage {
    modalTitle = element(by.css('h4#myPaiementLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dateTransationInput = element(by.css('input#field_dateTransation'));
    montantTTCInput = element(by.css('input#field_montantTTC'));
    consultationSelect = element(by.css('select#field_consultation'));
    moyenSelect = element(by.css('select#field_moyen'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDateTransationInput = function (dateTransation) {
        this.dateTransationInput.sendKeys(dateTransation);
    }

    getDateTransationInput = function () {
        return this.dateTransationInput.getAttribute('value');
    }

    setMontantTTCInput = function (montantTTC) {
        this.montantTTCInput.sendKeys(montantTTC);
    }

    getMontantTTCInput = function () {
        return this.montantTTCInput.getAttribute('value');
    }

    consultationSelectLastOption = function () {
        this.consultationSelect.all(by.tagName('option')).last().click();
    }

    consultationSelectOption = function (option) {
        this.consultationSelect.sendKeys(option);
    }

    getConsultationSelect = function () {
        return this.consultationSelect;
    }

    getConsultationSelectedOption = function () {
        return this.consultationSelect.element(by.css('option:checked')).getText();
    }

    moyenSelectLastOption = function () {
        this.moyenSelect.all(by.tagName('option')).last().click();
    }

    moyenSelectOption = function (option) {
        this.moyenSelect.sendKeys(option);
    }

    getMoyenSelect = function () {
        return this.moyenSelect;
    }

    getMoyenSelectedOption = function () {
        return this.moyenSelect.element(by.css('option:checked')).getText();
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
