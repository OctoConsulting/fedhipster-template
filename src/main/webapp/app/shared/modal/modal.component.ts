import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {
  isVisible = false;

  constructor() {}

  ngOnInit() {}

  show() {
    const body: HTMLElement = document.getElementById('main-body');
    if (body) {
      body.className = 'modal-open';
    }
    this.isVisible = true;
  }

  hide() {
    const body: HTMLElement = document.getElementById('main-body');
    if (body) {
      body.className = '';
    }
    this.isVisible = false;
  }
}
