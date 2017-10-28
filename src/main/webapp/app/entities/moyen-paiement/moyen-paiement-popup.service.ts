import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MoyenPaiement } from './moyen-paiement.model';
import { MoyenPaiementService } from './moyen-paiement.service';

@Injectable()
export class MoyenPaiementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private moyenPaiementService: MoyenPaiementService

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
                this.moyenPaiementService.find(id).subscribe((moyenPaiement) => {
                    this.ngbModalRef = this.moyenPaiementModalRef(component, moyenPaiement);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.moyenPaiementModalRef(component, new MoyenPaiement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    moyenPaiementModalRef(component: Component, moyenPaiement: MoyenPaiement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.moyenPaiement = moyenPaiement;
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
