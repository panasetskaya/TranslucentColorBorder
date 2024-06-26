[issuetracker](https://issuetracker.google.com/issues/349561253)

Translucent color and Outline.Generic Shape do not work well together in `Modifier.border()`. The color is rendered incorrectly.

![Screenshot 2024-06-26 at 16 00 29](https://github.com/panasetskaya/TranslucentColorBorder/assets/90948269/609c7b64-bdca-4864-a683-717387663061)

On the screenshot the translucent color for the border is used with: 
- the top one, working: standard RectangleShape
- the middle one, not working: custom Shape with Outline.Generic(somePath) outline, the somePath here in this case is the same Rect()
- the bottom one, working: a workaround, we use the same custom Shape, but do not pass the color to Modifier.border() directly, instead we give it `Brush.linearGradient(listOf(color, color))`
