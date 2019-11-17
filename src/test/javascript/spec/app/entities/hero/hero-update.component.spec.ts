/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { HeroUpdateComponent } from 'app/entities/hero/hero-update.component';
import { HeroService } from 'app/entities/hero/hero.service';
import { Hero } from 'app/shared/model/hero.model';

describe('Component Tests', () => {
  describe('Hero Management Update Component', () => {
    let comp: HeroUpdateComponent;
    let fixture: ComponentFixture<HeroUpdateComponent>;
    let service: HeroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [HeroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(HeroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HeroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(HeroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Hero(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Hero();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
