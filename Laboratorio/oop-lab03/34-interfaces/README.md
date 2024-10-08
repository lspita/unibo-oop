# Costruzione di classi incapsulate e interfacce

* Si completi la classe `SimpleBankAccount` seguendo le istruzioni riportate nei commenti della medesima

* Si testi l'implementazione utilizzando la classe `TestSimpleBankAccount`.

* Si modifichi la classe `SimpleBankAccount` in modo che implementi l'interfaccia `BankAccount`, realizzando solo i metodi necessari affinché l'interfaccia risulti correttamente implementata.
    - Si noti che per SimpleBankAccount non sono previste spese di gestione. Quindi, il metodo `chargeManagementFees()` va definito (in quanto previsto dall'interfaccia) ma avrà corpo vuoto.

* Si implementi in autonomia la classe `StrictBankAccount`, tale che:
    - `StrictBankAccount` implementa `BankAccount`
    - Le operazioni sul conto dovranno essere abilitate solo se lo user id corrisponde a quello a cui il conto è stato associato
    - Non dovrà essere possibile effettuare dei prelievi se l'ammontare da prelevare specificato è superiore alla disponibilità corrente del conto. Se il caso si verifica, le operazioni di prelievo non dovranno fare nulla.
    - Le spese di gestione dovranno essere calcolate come segue: al costo fisso di 5 Euro andrà aggiunto un costo variabile di 0.1 euro per ciascuna transazione effettuata

* Effettuare il test di `StrictBankAccount` seguendo le indicazioni fornite ed i commenti in `TestBankAccount`
