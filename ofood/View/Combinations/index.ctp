<div class="combinations index">
	<h2><?php echo __('Combinations'); ?></h2>
        <table cellpadding="0" class="table table-bordered" cellspacing="0">
	<thead>
	<tr>
			<th><?php echo $this->Paginator->sort('id'); ?></th>
			<th><?php echo $this->Paginator->sort('vendor_id'); ?></th>
			<th><?php echo $this->Paginator->sort('display_name'); ?></th>
			<th><?php echo $this->Paginator->sort('day'); ?></th>
			<th><?php echo $this->Paginator->sort('date'); ?></th>
			<th><?php echo $this->Paginator->sort('price'); ?></th>
			<th><?php echo $this->Paginator->sort('status'); ?></th>
			<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	</thead>
	<tbody>
	<?php foreach ($combinations as $combination): ?>
	<tr>
		<td><?php echo h($combination['Combination']['id']); ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($combination['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $combination['Vendor']['id'])); ?>
		</td>
		<td><?php echo h($combination['Combination']['display_name']); ?>&nbsp;</td>
		<td><?php echo h($combination['Combination']['day']); ?>&nbsp;</td>
		<td><?php echo h($combination['Combination']['date']); ?>&nbsp;</td>
		<td><?php echo h($combination['Combination']['price']); ?>&nbsp;</td>
		<td><?php echo h($combination['Combination']['status']); ?>&nbsp;</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View'), array('action' => 'view', $combination['Combination']['id'])); ?>
			<?php echo $this->Html->link(__('Edit'), array('action' => 'edit', $combination['Combination']['id'])); ?>
			<?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $combination['Combination']['id']), array(), __('Are you sure you want to delete # %s?', $combination['Combination']['id'])); ?>
		</td>
	</tr>
<?php endforeach; ?>
	</tbody>
	</table>
	<p>
	<?php
	echo $this->Paginator->counter(array(
	'format' => __('Page {:page} of {:pages}, showing {:current} records out of {:count} total, starting on record {:start}, ending on {:end}')
	));
	?>	</p>
	<div class="paging">
	<?php
		echo $this->Paginator->prev('< ' . __('previous'), array(), null, array('class' => 'prev disabled'));
		echo $this->Paginator->numbers(array('separator' => ''));
		echo $this->Paginator->next(__('next') . ' >', array(), null, array('class' => 'next disabled'));
	?>
	</div>
</div>