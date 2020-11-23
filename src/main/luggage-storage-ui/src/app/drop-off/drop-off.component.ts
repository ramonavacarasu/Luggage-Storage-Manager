import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { Locker } from '../model/locker';
import { AlertService } from '../shared/alert.service';
import { StowService } from '../shared/stow.service';
import * as fileSaver from 'file-saver';
import { ReleasedLuggage } from '../model/releasedLuggage';
import { FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-drop-off',
  templateUrl: './drop-off.component.html',
  styleUrls: ['./drop-off.component.css']
})
export class DropOffComponent implements OnInit {

  pickupBtnClicked: boolean = false;
  printCode: boolean = false;
  pickupMsg: boolean = false;
  locker: Locker = new Locker;
  releasedLuggage: ReleasedLuggage = new ReleasedLuggage;

  code$ = new FormControl('', Validators.required);

  numberTotalOfLockers!: string;
  numberOfAvailableLockers!: string;

  constructor(private stowService: StowService,
    private alertService: AlertService) { }

  ngOnInit() {
    this.stowService.numberTotalOfLockers().subscribe(res => {
      this.numberTotalOfLockers = res;
      console.log(res);
    });

    this.updateNumberOfAvailableLockers();
  }

  dropOff() {
    this.stowService.dropOff()
      .pipe(first())
      .subscribe({
        next: (locker$) => {
          if (locker$) {
            this.alertService.success("Your luggage was dropped successfully!", { autoClose: true });
            this.printCode = true;
            this.locker = locker$;
            this.updateNumberOfAvailableLockers();
          } else {
            this.alertService.error("Capacity full! No more storages available!");
          }
        },
        error: _error => {
          this.alertService.error("No connection with backend!", { autoClose: true });
        }
      });
  }

  pickUp() {
    this.pickupBtnClicked = true;
  }

  onSubmit() {
    this.stowService.submitCode(this.code$.value)
      .pipe(first())
      .subscribe({
        next: (releasedLuggage$) => {
          if (releasedLuggage$.luggageID !== null) {
            this.releasedLuggage = releasedLuggage$;
            this.pickupMsg = true;
            this.pickupBtnClicked = false;
          } else {
            this.alertService.error("Incorrect code. Try again!", { autoClose: true });
          }
        },
        error: error => {
          this.alertService.error("No connection with backend!", { autoClose: true });
        }
      });

    this.code$.reset();
  }

  cancelSubmit() {
    this.pickupBtnClicked = false;
  }

  download() {
    this.stowService.downloadFile(this.locker.id)
      .subscribe(res => {
        let blob: any = new Blob([res], { type: 'text/json; charset=utf-8' });
        const url = window.URL.createObjectURL(blob);
        fileSaver.saveAs(blob, 'luggage.txt');
      });
    this.printCode = false;
  }

  cancelDownload() {
    this.printCode = false;
  }

  releaseLuggage() {
    this.stowService.releaseLuggage(this.releasedLuggage.luggageID)
      .subscribe({
        next: () => {
          this.releasedLuggage = new ReleasedLuggage;
          this.alertService.success("Successfull payment! Please pick-up your luggage!", { autoClose: true });
          this.updateNumberOfAvailableLockers();
        }
      });
    this.pickupMsg = false;
  }

  updateNumberOfAvailableLockers() {
    this.stowService.numberOfAvailableLockers().subscribe(res => {
      this.numberOfAvailableLockers = res;
    });
  }
}