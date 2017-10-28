import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { RegimeSecuriteSociale } from './regime-securite-sociale.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RegimeSecuriteSocialeService {

    private resourceUrl = SERVER_API_URL + 'api/regime-securite-sociales';

    constructor(private http: Http) { }

    create(regimeSecuriteSociale: RegimeSecuriteSociale): Observable<RegimeSecuriteSociale> {
        const copy = this.convert(regimeSecuriteSociale);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(regimeSecuriteSociale: RegimeSecuriteSociale): Observable<RegimeSecuriteSociale> {
        const copy = this.convert(regimeSecuriteSociale);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<RegimeSecuriteSociale> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to RegimeSecuriteSociale.
     */
    private convertItemFromServer(json: any): RegimeSecuriteSociale {
        const entity: RegimeSecuriteSociale = Object.assign(new RegimeSecuriteSociale(), json);
        return entity;
    }

    /**
     * Convert a RegimeSecuriteSociale to a JSON which can be sent to the server.
     */
    private convert(regimeSecuriteSociale: RegimeSecuriteSociale): RegimeSecuriteSociale {
        const copy: RegimeSecuriteSociale = Object.assign({}, regimeSecuriteSociale);
        return copy;
    }
}
