import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Consultation e2e test', () => {

    let navBarPage: NavBarPage;
    let consultationDialogPage: ConsultationDialogPage;
    let consultationComponentsPage: ConsultationComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Consultations', () => {
        navBarPage.goToEntity('consultation');
        consultationComponentsPage = new ConsultationComponentsPage();
        expect(consultationComponentsPage.getTitle()).toMatch(/techmedApp.consultation.home.title/);

    });

    it('should load create Consultation dialog', () => {
        consultationComponentsPage.clickOnCreateButton();
        consultationDialogPage = new ConsultationDialogPage();
        expect(consultationDialogPage.getModalTitle()).toMatch(/techmedApp.consultation.home.createOrEditLabel/);
        consultationDialogPage.close();
    });

    it('should create and save Consultations', () => {
        consultationComponentsPage.clickOnCreateButton();
        consultationDialogPage.setDateActeInput(12310020012301);
        expect(consultationDialogPage.getDateActeInput()).toMatch('2001-12-31T02:30');
        consultationDialogPage.setMontantTTCInput('5');
        expect(consultationDialogPage.getMontantTTCInput()).toMatch('5');
        consultationDialogPage.setLockInput('lock');
        expect(consultationDialogPage.getLockInput()).toMatch('lock');
        consultationDialogPage.patientSelectLastOption();
        consultationDialogPage.medecinSelectLastOption();
        consultationDialogPage.regimeSecuriteSocialeSelectLastOption();
        // consultationDialogPage.actesMedicalListSelectLastOption();
        consultationDialogPage.save();
        expect(consultationDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class ConsultationComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-consultation div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ConsultationDialogPage {
    modalTitle = element(by.css('h4#myConsultationLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    dateActeInput = element(by.css('input#field_dateActe'));
    montantTTCInput = element(by.css('input#field_montantTTC'));
    lockInput = element(by.css('textarea#field_lock'));
    patientSelect = element(by.css('select#field_patient'));
    medecinSelect = element(by.css('select#field_medecin'));
    regimeSecuriteSocialeSelect = element(by.css('select#field_regimeSecuriteSociale'));
    actesMedicalListSelect = element(by.css('select#field_actesMedicalList'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDateActeInput = function (dateActe) {
        this.dateActeInput.sendKeys(dateActe);
    }

    getDateActeInput = function () {
        return this.dateActeInput.getAttribute('value');
    }

    setMontantTTCInput = function (montantTTC) {
        this.montantTTCInput.sendKeys(montantTTC);
    }

    getMontantTTCInput = function () {
        return this.montantTTCInput.getAttribute('value');
    }

    setLockInput = function (lock) {
        this.lockInput.sendKeys(lock);
    }

    getLockInput = function () {
        return this.lockInput.getAttribute('value');
    }

    patientSelectLastOption = function () {
        this.patientSelect.all(by.tagName('option')).last().click();
    }

    patientSelectOption = function (option) {
        this.patientSelect.sendKeys(option);
    }

    getPatientSelect = function () {
        return this.patientSelect;
    }

    getPatientSelectedOption = function () {
        return this.patientSelect.element(by.css('option:checked')).getText();
    }

    medecinSelectLastOption = function () {
        this.medecinSelect.all(by.tagName('option')).last().click();
    }

    medecinSelectOption = function (option) {
        this.medecinSelect.sendKeys(option);
    }

    getMedecinSelect = function () {
        return this.medecinSelect;
    }

    getMedecinSelectedOption = function () {
        return this.medecinSelect.element(by.css('option:checked')).getText();
    }

    regimeSecuriteSocialeSelectLastOption = function () {
        this.regimeSecuriteSocialeSelect.all(by.tagName('option')).last().click();
    }

    regimeSecuriteSocialeSelectOption = function (option) {
        this.regimeSecuriteSocialeSelect.sendKeys(option);
    }

    getRegimeSecuriteSocialeSelect = function () {
        return this.regimeSecuriteSocialeSelect;
    }

    getRegimeSecuriteSocialeSelectedOption = function () {
        return this.regimeSecuriteSocialeSelect.element(by.css('option:checked')).getText();
    }

    actesMedicalListSelectLastOption = function () {
        this.actesMedicalListSelect.all(by.tagName('option')).last().click();
    }

    actesMedicalListSelectOption = function (option) {
        this.actesMedicalListSelect.sendKeys(option);
    }

    getActesMedicalListSelect = function () {
        return this.actesMedicalListSelect;
    }

    getActesMedicalListSelectedOption = function () {
        return this.actesMedicalListSelect.element(by.css('option:checked')).getText();
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
