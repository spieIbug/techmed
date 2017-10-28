import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CodeCCAM e2e test', () => {

    let navBarPage: NavBarPage;
    let codeCCAMDialogPage: CodeCCAMDialogPage;
    let codeCCAMComponentsPage: CodeCCAMComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-doctor.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CodeCCAMS', () => {
        navBarPage.goToEntity('code-ccam');
        codeCCAMComponentsPage = new CodeCCAMComponentsPage();
        expect(codeCCAMComponentsPage.getTitle()).toMatch(/techmedApp.codeCCAM.home.title/);

    });

    it('should load create CodeCCAM dialog', () => {
        codeCCAMComponentsPage.clickOnCreateButton();
        codeCCAMDialogPage = new CodeCCAMDialogPage();
        expect(codeCCAMDialogPage.getModalTitle()).toMatch(/techmedApp.codeCCAM.home.createOrEditLabel/);
        codeCCAMDialogPage.close();
    });

    it('should create and save CodeCCAMS', () => {
        codeCCAMComponentsPage.clickOnCreateButton();
        codeCCAMDialogPage.setCodeInput('code');
        expect(codeCCAMDialogPage.getCodeInput()).toMatch('code');
        codeCCAMDialogPage.setLibelleInput('libelle');
        expect(codeCCAMDialogPage.getLibelleInput()).toMatch('libelle');
        codeCCAMDialogPage.save();
        expect(codeCCAMDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CodeCCAMComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-code-ccam div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CodeCCAMDialogPage {
    modalTitle = element(by.css('h4#myCodeCCAMLabel'));
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
