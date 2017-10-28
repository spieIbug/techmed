import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RegimeSecuriteSociale } from './regime-securite-sociale.model';
import { RegimeSecuriteSocialeService } from './regime-securite-sociale.service';

@Injectable()
export class RegimeSecuriteSocialePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private regimeSecuriteSocialeService: RegimeSecuriteSocialeService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.regimeSecuriteSocialeService.find(id).subscribe((regimeSecuriteSociale) => {
                    this.ngbModalRef = this.regimeSecuriteSocialeModalRef(component, regimeSecuriteSociale);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.regimeSecuriteSocialeModalRef(component, new RegimeSecuriteSociale());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    regimeSecuriteSocialeModalRef(component: Component, regimeSecuriteSociale: RegimeSecuriteSociale): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.regimeSecuriteSociale = regimeSecuriteSociale;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
