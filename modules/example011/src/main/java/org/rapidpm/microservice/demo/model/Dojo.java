package org.rapidpm.microservice.demo.model;

/**
 * Copyright (C) 2010 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created by RapidPM - Team on 24.11.2016.
 */
public class Dojo {

  private Dojo() {
  }

  public static String fight(final Duel duel) {
    final Fighter fighter01 = duel.fighter01;
    final Fighter fighter02 = duel.fighter02;
    String starter = fighter01.name;

    while (fighter01.health > 0 && fighter02.health > 0) {
      if (starter.equals(fighter01.name)) {
        fighter02.health = fighter02.health - fighter01.damage;
        starter = fighter02.name;
      } else {
        fighter01.health = fighter01.health - fighter02.damage;
        starter = fighter01.name;
      }
    }

    return fighter01.health > 0 ? fighter01.name : fighter02.name;
  }

}
