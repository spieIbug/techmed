import {BaseEntity} from './../../shared';

export class Tarif implements BaseEntity {
    constructor(public id?: number,
                private _prixHT?: number,
                private _tva?: number,
                private _prixTTC?: number,
                public actif?: boolean,
                public dateDebut?: any,
                public dateFin?: any,
                public acteMedicalId?: number) {

        _prixHT = 0;
        _tva = 0;
        _prixTTC = 0;
        this.actif = false;
    }

    public get tva(): number {
        return this._tva;
    }

    public set tva(value: number) {
        this._tva = value;
        this._prixTTC = this._prixHT * (100 + this._tva) / 100;
    }

    public set prixHT(value: number) {
        this._prixHT = value;
        this._prixTTC = this._prixHT * (100 + this._tva) / 100;
    }

    public get prixHT(): number {
        return this._prixHT;
    }

    public get prixTTC(): number {
        return this._prixTTC;
    }

    public set prixTTC(value: number) {
        this._prixTTC = value;
        this._prixHT = this._prixTTC * 100 / (100 + this._tva);
    }
}
