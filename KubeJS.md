# Using KubeJs to add centrifuge recipes

`event.createestrogen.centrifuging(outputFluid, InputFluids[])`
> Warning! multiple input fluids are not supported so here be dragons.
example:
```js
ServerEvents.recipes(event => {
    // adds a recipe which uses 10mb/tick of water to create 10mb/tick of lava
    event.recipes.createestrogen.centrifuging(Fluid.of('minecraft:lava',10),[Fluid.of('minecraft:water',10)])
})
```