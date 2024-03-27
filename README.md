Els diagrames de classes i casos d'us es troben dins 
del directori /docs/diagrames.

La documentació del projecte ha estat generada amb Javadoc i
es també accessible des del directori /docs buscant el index.html
i obrint-ho al navegador es pot navegar per la documentació de
les diferents classes i paquets.

Per provar l'execució del codi java hem creat una classe main fora
dels paquets de domini, persistencia i presentacio (per ara buit)
des d'on executar el joc temporalment implementat.

Un cop executem el main() podem introduïr les següents comandes:

crear_usuari    -> aquesta comanda ens pemetrà afegir un usuari a la 
base de dades de usuaris seguint les intruccions per consola (si existeix
no l'afegirà a la base de dades feta amb json).

login      -> aquesta comanda ens permetrà loguejarnos com a usuari_principal
si existim a la bd de json.

ia_vs_ia   -> aquesta comanda ens permetrà iniciar una partida en la que s'enfrenten 2 IAs
i es visualitzarà la partida per consola, podrem definir les dificultats de cada una de les
IAs i veure la execucio del joc torn per torn com indiquin les intruccions de consola.

ia_vs_persona    -> aquesta comanda permetrà que juguem contra una IA amb una dificultat
que nosaltres definim. seguint les instruccions de la consola podrem jugar.
sortir   -> termina el joc i acaba la execució

