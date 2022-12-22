create definer = root@localhost view tender.view_maximalni_bod as
select `tender`.`view_vrednovanje`.`broj_partije`    AS `broj_partije`,
`tender`.`view_vrednovanje`.`sifra_postupka`  AS `sifra_postupka`,
max(`tender`.`view_vrednovanje`.`bod_ukupno`) AS `maximalni_bod`
from `tender`.`view_vrednovanje`
group by `tender`.`view_vrednovanje`.`broj_partije`, `tender`.`view_vrednovanje`.`sifra_postupka`
order by `tender`.`view_vrednovanje`.`broj_partije`;

create definer = root@localhost view tender.view_ponude as
select `tender`.`ponude`.`id`                   AS `id`,
`tender`.`ponude`.`sifra_postupka`       AS `sifra_postupka`,
`tender`.`ponude`.`sifra_ponude`         AS `sifra_ponude`,
`tender`.`ponude`.`broj_partije`         AS `broj_partije`,
`tender`.`ponude`.`naziv_proizvodjaca`   AS `naziv_proizvodjaca`,
`tender`.`ponude`.`zasticeni_naziv`      AS `zasticeni_naziv`,
`tender`.`ponude`.`ponudjena_vrijednost` AS `ponudjena_vrijednost`,
`tender`.`ponude`.`rok_isporuke`         AS `rok_isporuke`,
`tender`.`ponude`.`jedinicna_cijena`     AS `jedinicna_cijena`,
`tender`.`ponude`.`selected`             AS `selected`,
`tender`.`ponude`.`sifra_ponudjaca`      AS `sifra_ponudjaca`,
`tender`.`ponude`.`karakteristika`       AS `karakteristika`,
`tender`.`ponude`.`created_by`           AS `created_by`,
`tender`.`ponude`.`created_date`         AS `created_date`,
`tender`.`ponude`.`last_modified_by`     AS `last_modified_by`,
`tender`.`ponude`.`last_modified_date`   AS `last_modified_date`,
`tender`.`ponudjaci`.`naziv_ponudjaca`   AS `naziv_ponudjaca`
from (`tender`.`ponude` join `tender`.`ponudjaci`
on ((`tender`.`ponude`.`sifra_ponudjaca` = `tender`.`ponudjaci`.`id`)));

create definer = root@localhost view tender.view_prvorangirani as
select `tender`.`view_vrednovanje`.`id`                           AS `id`,
`tender`.`view_vrednovanje`.`sifra_postupka`               AS `sifra_postupka`,
`tender`.`view_vrednovanje`.`naziv_ponudjaca`              AS `naziv_ponudjaca`,
`tender`.`view_vrednovanje`.`sifra_ponude`                 AS `sifra_ponude`,
`tender`.`view_vrednovanje`.`broj_partije`                 AS `broj_partije`,
`tender`.`view_vrednovanje`.`atc`                          AS `atc`,
`tender`.`view_vrednovanje`.`trazena_kolicina`             AS `trazena_kolicina`,
`tender`.`view_vrednovanje`.`procijenjena_vrijednost`      AS `procijenjena_vrijednost`,
`tender`.`view_vrednovanje`.`naziv_proizvodjaca`           AS `naziv_proizvodjaca`,
`tender`.`view_vrednovanje`.`zasticeni_naziv`              AS `zasticeni_naziv`,
`tender`.`view_vrednovanje`.`karakteristika_ponude`        AS `karakteristika_ponude`,
`tender`.`view_vrednovanje`.`karakteristika_specifikacije` AS `karakteristika_specifikacije`,
`tender`.`view_vrednovanje`.`jedinicna_cijena`             AS `jedinicna_cijena`,
`tender`.`view_vrednovanje`.`ponudjena_vrijednost`         AS `ponudjena_vrijednost`,
`tender`.`view_vrednovanje`.`rok_isporuke`                 AS `rok_isporuke`,
`tender`.`view_vrednovanje`.`vrsta_postupka`               AS `vrsta_postupka`,
`tender`.`view_vrednovanje`.`bod_cijena`                   AS `bod_cijena`,
`tender`.`view_vrednovanje`.`bod_rok`                      AS `bod_rok`,
`tender`.`view_vrednovanje`.`bod_ukupno`                   AS `bod_ukupno`
from (`tender`.`view_vrednovanje` join `tender`.`view_maximalni_bod`
on (((`tender`.`view_vrednovanje`.`broj_partije` = `tender`.`view_maximalni_bod`.`broj_partije`) and
(`tender`.`view_vrednovanje`.`sifra_postupka` = `tender`.`view_maximalni_bod`.`sifra_postupka`) and
(`tender`.`view_vrednovanje`.`bod_ukupno` = `tender`.`view_maximalni_bod`.`maximalni_bod`))));

create definer = root@localhost view tender.view_vrednovanje as
select distinct `tender`.`ponude`.`id`                                                            AS `id`,
`tender`.`ponude`.`sifra_postupka`                                                AS `sifra_postupka`,
`tender`.`ponude`.`sifra_ponude`                                                  AS `sifra_ponude`,
`tender`.`ponude`.`broj_partije`                                                  AS `broj_partije`,
`tender`.`ponude`.`naziv_proizvodjaca`                                            AS `naziv_proizvodjaca`,
`tender`.`ponude`.`zasticeni_naziv`                                               AS `zasticeni_naziv`,
`tender`.`ponude`.`ponudjena_vrijednost`                                          AS `ponudjena_vrijednost`,
`tender`.`ponude`.`rok_isporuke`                                                  AS `rok_isporuke`,
`tender`.`ponude`.`jedinicna_cijena`                                              AS `jedinicna_cijena`,
`tender`.`ponude`.`karakteristika`                                                AS `karakteristika_ponude`,
`tender`.`ponudjaci`.`naziv_ponudjaca`                                            AS `naziv_ponudjaca`,
`tender`.`specifikacije`.`atc`                                                    AS `atc`,
`tender`.`specifikacije`.`karakteristika`                                         AS `karakteristika_specifikacije`,
`tender`.`specifikacije`.`trazena_kolicina`                                       AS `trazena_kolicina`,
`tender`.`specifikacije`.`procijenjena_vrijednost`                                AS `procijenjena_vrijednost`,
`tender`.`postupci`.`vrsta_postupka`                                              AS `vrsta_postupka`,
((`tender`.`postupci`.`kriterijum_cijena` * min(`tender`.`ponude`.`ponudjena_vrijednost`)
OVER (PARTITION BY `tender`.`ponude`.`broj_partije`,`tender`.`ponude`.`sifra_postupka` )) /
`tender`.`ponude`.`ponudjena_vrijednost`)                                        AS `bod_cijena`,
((`tender`.`postupci`.`drugi_kriterijum` * min(`tender`.`ponude`.`rok_isporuke`)
OVER (PARTITION BY `tender`.`ponude`.`broj_partije`,`tender`.`ponude`.`sifra_postupka` )) /
`tender`.`ponude`.`rok_isporuke`)                                                AS `bod_rok`,
(((`tender`.`postupci`.`kriterijum_cijena` * min(`tender`.`ponude`.`ponudjena_vrijednost`)
OVER (PARTITION BY `tender`.`ponude`.`broj_partije`,`tender`.`ponude`.`sifra_postupka` )) /
`tender`.`ponude`.`ponudjena_vrijednost`) + ((`tender`.`postupci`.`drugi_kriterijum` *
min(`tender`.`ponude`.`rok_isporuke`)
OVER (PARTITION BY `tender`.`ponude`.`broj_partije`,`tender`.`ponude`.`sifra_postupka` )) /
`tender`.`ponude`.`rok_isporuke`)) AS `bod_ukupno`
from (((`tender`.`ponude` join `tender`.`postupci`
on ((`tender`.`ponude`.`sifra_postupka` = `tender`.`postupci`.`sifra_postupka`))) join `tender`.`ponudjaci`
on ((`tender`.`ponude`.`sifra_ponudjaca` = `tender`.`ponudjaci`.`id`))) join `tender`.`specifikacije`
on (((`tender`.`ponude`.`sifra_postupka` = `tender`.`specifikacije`.`sifra_postupka`) and
(`tender`.`ponude`.`broj_partije` = `tender`.`specifikacije`.`broj_partije`))));

