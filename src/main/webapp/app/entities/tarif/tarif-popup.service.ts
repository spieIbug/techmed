import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Tarif } from './tarif.model';
import { TarifService } from './tarif.service';

@Injectable()
export class TarifPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tarifService: TarifService

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
                this.tarifService.find(id).subscribe((tarif) => {
                    tarif.dateDebut = this.datePipe
                        .transform(tarif.dateDebut, 'yyyy-MM-ddTHH:mm:ss');
                    tarif.dateFin = this.datePipe
                        .transform(tarif.dateFin, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.tarifModalRef(component, tarif);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tarifModalRef(component, new Tarif());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tarifModalRef(component: Component, tarif: Tarif): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tarif = tarif;
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
