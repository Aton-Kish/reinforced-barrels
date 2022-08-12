![Reinforced Barrels](./images/header.png)

[![Mod Loader: Fabric](https://img.shields.io/static/v1?label=modloader&message=fabric&color=brightgreen)](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
![Mod Environment](https://img.shields.io/static/v1?label=environment&message=client%2Fserver&color=yellow)
[![Downloads](https://raw.githubusercontent.com/Aton-Kish/mcmod-stats/main/reinforced-barrels/downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/reinforced-barrels)
[![MIT License](https://img.shields.io/static/v1?label=licence&message=MIT&color=blue)](./LICENSE)
[![build](https://github.com/Aton-Kish/reinforced-barrels/actions/workflows/build.yaml/badge.svg?branch=1.19)](https://github.com/Aton-Kish/reinforced-barrels/actions/workflows/build.yaml?query=branch:1.19)

# Reinforced Barrels

The Reinforced Barrels mod adds reinforced barrels.

[<img alt="Requires Fabric API" src="https://i.imgur.com/Ol1Tcf8.png" width="128"/>](https://www.curseforge.com/minecraft/mc-mods/fabric-api)

## Reinforced Storage Mod Series

- [Reinforced Chests](https://github.com/Aton-Kish/reinforced-chests)
- [Reinforced Shulker Boxes](https://github.com/Aton-Kish/reinforced-shulker-boxes)

## Recipe

| Name             | Type            | Ingredients                      | Recipe                                                                                                 | Description                                                                                             |
| ---------------- | --------------- | -------------------------------- | ------------------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------------------------------------- |
| Copper Barrel    | Shaped Crafting | Barrel + Copper Ingot            | <img alt="Copper Barrel Recipe" src="./images/recipes/copper_barrel.png" width="256" />                | A copper barrels has a container inventory with 45 slots.                                               |
| Iron Barrel      | Shaped Crafting | Copper Barrel + Iron Ingot       | <img alt="Iron Barrel Recipe" src="./images/recipes/iron_barrel.png" width="256" />                    | An iron barrels has a container inventory with 54 slots.                                                |
| Gold Barrel      | Shaped Crafting | Iron Barrel + Gold Ingot         | <img alt="Gold Barrel Recipe" src="./images/recipes/gold_barrel.png" width="256" />                    | A gold barrels has a container inventory with 81 slots.                                                 |
| Diamond Barrel   | Shaped Crafting | Gold Barrel + Diamond            | <img alt="Diamond Barrel Recipe" src="./images/recipes/diamond_barrel.png" width="256" />              | A diamond barrels has a container inventory with 108 slots.                                             |
| Netherite Barrel | Smithing        | Diamond Barrel + Netherite Ingot | <img alt="Netherite Barrel Recipe" src="./images/recipes/netherite_barrel_smithing.png" width="256" /> | A netherite barrel has a container inventory with 108 slots. This is resistant to blast, fire and lava. |

## Configure

[The Reinforced Core lib](https://github.com/Aton-Kish/reinforced-core) has been integrated with [Mod Menu](https://www.curseforge.com/minecraft/mc-mods/modmenu) since version 3.0.0.

![Mod Menu](./images/modmenu/modmenu.png)

### Screen Type

_Available in Reinforced Barrels mod version 2.2.0+._

Screen type is `SINGLE` or `SCROLL`. (default: `SINGLE`)

| `SINGLE` screen                               | `SCROLL` screen                                |
| --------------------------------------------- | ---------------------------------------------- |
| ![Single Screen](./images/modmenu/single.png) | ![Scroll Screen](./images/modmenu/scroll6.png) |

### Scroll Screen

#### Rows

_Available in Reinforced Barrels mod version 2.2.0+._

Rows is an integer in the range from `6` to `9`. (default: `6`)

| Rows: `6`                                              | Rows: `9`                                              |
| ------------------------------------------------------ | ------------------------------------------------------ |
| ![Scroll Screen: 6 rows](./images/modmenu/scroll6.png) | ![Scroll Screen: 9 rows](./images/modmenu/scroll9.png) |

## License

The Reinforced Barrels mod is licensed under the MIT License, see [LICENSE](./LICENSE).
