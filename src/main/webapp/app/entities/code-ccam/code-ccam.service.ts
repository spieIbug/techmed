import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CodeCCAM } from './code-ccam.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CodeCCAMService {

    private resourceUrl = SERVER_API_URL + 'api/code-ccams';

    constructor(private http: Http) { }

    create(codeCCAM: CodeCCAM): Observable<CodeCCAM> {
        const copy = this.convert(codeCCAM);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(codeCCAM: CodeCCAM): Observable<CodeCCAM> {
        const copy = this.convert(codeCCAM);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CodeCCAM> {
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
     * Convert a returned JSON object to CodeCCAM.
     */
    private convertItemFromServer(json: any): CodeCCAM {
        const entity: CodeCCAM = Object.assign(new CodeCCAM(), json);
        return entity;
    }

    /**
     * Convert a CodeCCAM to a JSON which can be sent to the server.
     */
    private convert(codeCCAM: CodeCCAM): CodeCCAM {
        const copy: CodeCCAM = Object.assign({}, codeCCAM);
        return copy;
    }
}
