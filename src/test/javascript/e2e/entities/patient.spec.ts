import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Patient e2e test', () => {

    let navBarPage: NavBarPage;
    let patientDialogPage: PatientDialogPage;
    let patientComponentsPage: PatientComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-doctor.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Patients', () => {
        navBarPage.goToEntity('patient');
        patientComponentsPage = new PatientComponentsPage();
        expect(patientComponentsPage.getTitle()).toMatch(/techmedApp.patient.home.title/);

    });

    it('should load create Patient dialog', () => {
        patientComponentsPage.clickOnCreateButton();
        patientDialogPage = new PatientDialogPage();
        expect(patientDialogPage.getModalTitle()).toMatch(/techmedApp.patient.home.createOrEditLabel/);
        patientDialogPage.close();
    });

    it('should create and save Patients', () => {
        patientComponentsPage.clickOnCreateButton();
        patientDialogPage.setNomInput('nom');
        expect(patientDialogPage.getNomInput()).toMatch('nom');
        patientDialogPage.setPrenomInput('prenom');
        expect(patientDialogPage.getPrenomInput()).toMatch('prenom');
        patientDialogPage.setDateNaissanceInput(12310020012301);
        expect(patientDialogPage.getDateNaissanceInput()).toMatch('2001-12-31T02:30');
        patientDialogPage.save();
        expect(patientDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PatientComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-patient div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PatientDialogPage {
    modalTitle = element(by.css('h4#myPatientLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nomInput = element(by.css('input#field_nom'));
    prenomInput = element(by.css('input#field_prenom'));
    dateNaissanceInput = element(by.css('input#field_dateNaissance'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setNomInput = function (nom) {
        this.nomInput.sendKeys(nom);
    }

    getNomInput = function () {
        return this.nomInput.getAttribute('value');
    }

    setPrenomInput = function (prenom) {
        this.prenomInput.sendKeys(prenom);
    }

    getPrenomInput = function () {
        return this.prenomInput.getAttribute('value');
    }

    setDateNaissanceInput = function (dateNaissance) {
        this.dateNaissanceInput.sendKeys(dateNaissance);
    }

    getDateNaissanceInput = function () {
        return this.dateNaissanceInput.getAttribute('value');
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
