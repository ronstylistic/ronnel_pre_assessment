import { Component, OnDestroy } from '@angular/core'; import { RestError } from 'src/app/models/api.io';


@Component({
  selector: 'app-custom-alert',
  templateUrl: 'custom-alert.component.html',
  styleUrls: ['custom-alert.component.scss'],
})

export class CustomAlertComponent implements OnDestroy {
    message: RestError = new RestError();

    constructor() {

    }

    ngOnDestroy(): void {

    }

    hide(): void {
      this.message = new RestError();
    }

    show(message: RestError): void {
      this.message = message;
    }

    showError(text: string): void {
      this.message = new RestError();
      this.message.txt = text;
      this.message.code = 500;
    }

    showSuccess(text: string): void {
      this.message = new RestError();
      this.message.txt = text;
      this.message.code = 200;
    }
}
