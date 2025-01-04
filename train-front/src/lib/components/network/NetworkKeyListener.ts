import type { NetworkModel } from "./NetworkModel.svelte"

export function registerKeyListener(model: NetworkModel) {
  const listener = (event: KeyboardEvent) => {
    if (event.key === 'Escape') {
      model.cancelActiveBehaviour()
    } else if (event.key === 'Delete' && model.selectedNodeId) {
      model.decommissionSelectedNode()
    } else if (event.key === 'Delete' && model.selectedTrackId) {
      model.decommissionSelectTrack()
    }
  }
  document.addEventListener('keydown', listener)
  return () => document.removeEventListener('keydown', listener)
}