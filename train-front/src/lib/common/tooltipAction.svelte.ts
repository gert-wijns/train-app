
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
    message: string
}

export function tooltip(element: HTMLElement, paramsFn: () => TooltipParams) {
    function onMouseEnter() {
        const params = paramsFn();
        const message = params.message.trim();
        if (message.length > 0) {
            const rect = element.getBoundingClientRect()
            tooltipData.top = rect.top
            tooltipData.right = rect.right
            tooltipData.bottom = rect.bottom
            tooltipData.left = rect.left
            tooltipData.tooltipText = message
            tooltipData.visibility = "visible"
            tooltipData.tooltipClass = params.class || "tooltip-info"
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