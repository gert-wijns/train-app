
export let tooltipData = $state({
    visibility: "hidden",
    top: 0,
    right: 0,
    bottom: 0,
    left: 0,
    tooltipText: "",
    tooltipClass: ""
})

export type TooltipParams = {
    class?: string,
    messages: string[]
}

export function tooltip(element: HTMLElement, paramsFn: () => TooltipParams) {
    function onMouseEnter() {
        const params = paramsFn();
        if (params.messages.length > 0) {
            const rect = element.getBoundingClientRect()
            tooltipData.top = rect.top
            tooltipData.right = rect.right
            tooltipData.bottom = rect.bottom
            tooltipData.left = rect.left
            tooltipData.tooltipText = params.messages.join('\n')
            tooltipData.visibility = "visible"
            tooltipData.tooltipClass = (params.class || "tooltip-info")
        }
    }
    function onMouseExit() {
        tooltipData.visibility = "hidden"
    }
    element.addEventListener("mouseenter", onMouseEnter)
    element.addEventListener("mouseleave", onMouseExit)
    return {
        destroy() {
            tooltipData.visibility = "hidden"
            element.removeEventListener("mouseenter", onMouseEnter)
            element.removeEventListener("mouseleave", onMouseExit)
        }
    }
} 