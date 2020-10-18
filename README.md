# jeuAssemblage
Le but de ce devoir est de réaliser une application de jeu, dotée d'une interface graphique, qui consiste à assembler des formes de sorte qu'elles occupent le moins de place possible. L'idée s'inspire du fameux Tetris, mais les modalités sont différentes :
- toutes les pièces sont exposées sur l'aire de jeu dès le début de la partie.
- à tout moment, le joueur peut choisir une pièce en cliquant dessus, et a alors la possibilité de la faire tourner ou de la déplacer.
- son but est de minimiser l'espace occupé par l'ensemble des pièces. Plus précisément, la fonction d'évaluation sera l'aire du plus petit rectangle (parallèle aux axes) recouvrant l'ensemble des pièces.
- lorsque le joueur considère avoir terminé (ou lorsque le nombre maximum d'actions autorisées est atteint), il clique sur un bouton est son score est alors calculé.
- à chaque partie, il est possible soit de demander à obtenir une nouvelle configuration de départ, soit charger une configuration déjà créée et sauvegardée. Dans ce dernier cas, le meilleur score ainsi que le nom du joueur correspondant seront sauvegardés.
- une option permettra de faire jouer l'ordinateur. Réaliser un bon joueur robot nécessiterait des connaissances solides en IA, mais vous essaierez de faire mieux que le hasard.


# Participants
-VINCENT Leo - 21805239
-BELLEBON Alexandre - 21808613
-LEROY Clementine - 21800424
-DEROUIN Aureline - 21806986

# Commandes
Depuis la racine: cd PiecePuzzle && ant compile && ant run
Depuis la racine: cd jeuAssemblage && ant compile && ant run


