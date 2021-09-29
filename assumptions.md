Menu/Game Interface
- The game mode is standard by default, unless another one is selected (Berserker/Survival)
- Actions can be performed during the paused state (placing buildings, equipping/unequipping items)

Player:
- The player cannot place buildings during when the battle interface, shop interface, or any other interfaces are open.
- The player can only drag weapons into weapon slots, armour into the armour slot, a helmet into the helmet slot and a shield into the shield slot.
- The player can only select one game mode (Survival, Berserker or Survival)
- The player can restart the game after winning or losing

Moving Entity:
- Multiple moving entities (character, enemies) can be on the same path position

Character
- The character can only move forward in the ordered path
- The character can battle multiple enemies (up to 5)
- The character may earn cards after winning battles
- The character may not receive more items (through buying or receiving rewards) if the unequipped inventory is full
- Always starts with 100 health at the commencement of the game
- Always start with 10 base damage at the commencement of the game
- Does not begin with any items, gold, XP or allied soldiers

Battle State:
- There is a maximum of 1 campfire building and 1 tower building that can support the character in any given battle
- There may be multiple enemies of the same type in the battle
- Zombified soldiers cannot be tranced back to fight for the character
- Tranced enemies cannot be zombified
- Enemies will always attack allied soldiers before the character
- Rewards after the battle depend on the number and type of enemies defeated

Allied Soldier:
- There is a maximum of 3 allied soldiers supporting the character at any given time.
- Allied soldiers cannot equip items or use consumables
- Allied soldiers have a set health of 30 and damage of 10
- Allied soldiers do not have any type of special modifier on their attack/defence (such as campfire effect)

Buildings:
- If multiple enemies run on a trap at the same time, they all receive the damage from the trap
- Villages heal the character for 60 health points when said character passes through
- If the character encounters an enemy and passes the barracks building at the SAME time, the allied soldier will join the character first

Item:
- Items are indestructible (except healing potions and one rings which are consumables)
- Consumption of health potion will result in automatic disappearance of said health potion
- The character may have multiple One Rings -> if the character dies, only one is used
- The attack reduction effects of defence items are multiplicative (e.g. shield gives 20% reduction, armour gives 20% reduction = 36% reduction)
- Health potion cannot be used when health is full

Enemies:
- If there 2 or more enemies on the map, no more will spawn (unless Zombie Pits/Vampire Castles spawn zombies/vampires)
- If there are less than 2 enemies, more enemies may spawn
- Enemies may move in both directions on the ordered path
- Enemies will not spawn on top of loot
- Enemies will not spawn on top of each other

Vampire:
- If a vampire is in between two campfires, it will just move down the path

Zombie:
- Zombies move every 2 ticks (versus 1 tick for Slug, Vampire, Character)
    