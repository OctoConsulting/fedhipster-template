import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IHero, Hero } from 'app/shared/model/hero.model';
import { HeroService } from './hero.service';

@Component({
  selector: 'jhi-hero-update',
  templateUrl: './hero-update.component.html'
})
export class HeroUpdateComponent implements OnInit {
  hero: IHero;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    affilition: [],
    eyeColor: [],
    hairColor: [],
    gender: [],
    status: [],
    year: [],
    firstAppearance: [],
    universe: [],
    intelligence: [],
    strength: [],
    speed: [],
    durability: [],
    power: [],
    combat: [],
    total: []
  });

  constructor(protected heroService: HeroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ hero }) => {
      this.updateForm(hero);
      this.hero = hero;
    });
  }

  updateForm(hero: IHero) {
    this.editForm.patchValue({
      id: hero.id,
      name: hero.name,
      affilition: hero.affilition,
      eyeColor: hero.eyeColor,
      hairColor: hero.hairColor,
      gender: hero.gender,
      status: hero.status,
      year: hero.year,
      firstAppearance: hero.firstAppearance,
      universe: hero.universe,
      intelligence: hero.intelligence,
      strength: hero.strength,
      speed: hero.speed,
      durability: hero.durability,
      power: hero.power,
      combat: hero.combat,
      total: hero.total
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const hero = this.createFromForm();
    if (hero.id !== undefined) {
      this.subscribeToSaveResponse(this.heroService.update(hero));
    } else {
      this.subscribeToSaveResponse(this.heroService.create(hero));
    }
  }

  private createFromForm(): IHero {
    const entity = {
      ...new Hero(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      affilition: this.editForm.get(['affilition']).value,
      eyeColor: this.editForm.get(['eyeColor']).value,
      hairColor: this.editForm.get(['hairColor']).value,
      gender: this.editForm.get(['gender']).value,
      status: this.editForm.get(['status']).value,
      year: this.editForm.get(['year']).value,
      firstAppearance: this.editForm.get(['firstAppearance']).value,
      universe: this.editForm.get(['universe']).value,
      intelligence: this.editForm.get(['intelligence']).value,
      strength: this.editForm.get(['strength']).value,
      speed: this.editForm.get(['speed']).value,
      durability: this.editForm.get(['durability']).value,
      power: this.editForm.get(['power']).value,
      combat: this.editForm.get(['combat']).value,
      total: this.editForm.get(['total']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHero>>) {
    result.subscribe((res: HttpResponse<IHero>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
