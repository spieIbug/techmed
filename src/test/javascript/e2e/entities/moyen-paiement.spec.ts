import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('MoyenPaiement e2e test', () => {

    let navBarPage: NavBarPage;
    let moyenPaiementDialogPage: MoyenPaiementDialogPage;
    let moyenPaiementComponentsPage: MoyenPaiementComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load MoyenPaiements', () => {
        navBarPage.goToEntity('moyen-paiement');
        moyenPaiementComponentsPage = new MoyenPaiementComponentsPage();
        expect(moyenPaiementComponentsPage.getTitle()).toMatch(/techmedApp.moyenPaiement.home.title/);

    });

    it('should load create MoyenPaiement dialog', () => {
        moyenPaiementComponentsPage.clickOnCreateButton();
        moyenPaiementDialogPage = new MoyenPaiementDialogPage();
        expect(moyenPaiementDialogPage.getModalTitle()).toMatch(/techmedApp.moyenPaiement.home.createOrEditLabel/);
        moyenPaiementDialogPage.close();
    });

    it('should create and save MoyenPaiements', () => {
        moyenPaiementComponentsPage.clickOnCreateButton();
        moyenPaiementDialogPage.setModeInput('mode');
        expect(moyenPaiementDialogPage.getModeInput()).toMatch('mode');
        moyenPaiementDialogPage.save();
        expect(moyenPaiementDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MoyenPaiementComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-moyen-paiement div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MoyenPaiementDialogPage {
    modalTitle = element(by.css('h4#myMoyenPaiementLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    modeInput = element(by.css('input#field_mode'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setModeInput = function (mode) {
        this.modeInput.sendKeys(mode);
    }

    getModeInput = function () {
        return this.modeInput.getAttribute('value');
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
