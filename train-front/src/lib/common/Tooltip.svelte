<script lang="ts">
  import { tooltipData } from "./tooltipAction.svelte";

  let tooltipEl: HTMLDivElement;
  let parentEl: HTMLDivElement;
  let timeoutId: undefined | NodeJS.Timeout = undefined;
  $effect(() => {
    tooltipEl.style.left = tooltipData.left - 2 + "px";
    tooltipEl.style.top = tooltipData.top - 2 + "px";
    tooltipEl.style.minWidth = tooltipData.right - tooltipData.left + 4 + "px";
    tooltipEl.style.minHeight = tooltipData.bottom - tooltipData.top + 4 + "px";
    tooltipEl.style.visibility = tooltipData.visibility;
    if (tooltipData.visibility === "hidden") {
      tooltipEl.style.opacity = "0";
      clearTimeout(timeoutId);
    } else {
      timeoutId = setTimeout(() => (tooltipEl.style.opacity = "1"), 500);
    }

    tooltipEl.parentElement?.removeChild(tooltipEl);
    const dialogs = document.getElementsByTagName("dialog");
    let appended = false;
    for (let i = 0; i < dialogs.length; i++) {
      const dialog = dialogs.item(i);
      if (dialog && dialog.open) {
        dialog.appendChild(tooltipEl);
        appended = true;
      }
    }
    if (!appended) {
      parentEl.appendChild(tooltipEl);
    }
  });
</script>

<div bind:this={parentEl}>
  <div
    bind:this={tooltipEl}
    class={"absolute pointer-events-none tooltip tooltip-open z-10 transition-opacity ease-in-out " +
      tooltipData.tooltipClass}
    data-tip={tooltipData.tooltipText}
  ></div>
</div>
