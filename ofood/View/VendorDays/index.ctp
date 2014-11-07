<div class="vendorDays index">
	<h2><?php echo __('Vendor Days'); ?></h2>
	<table cellpadding="0" cellspacing="0">
	<thead>
	<tr>
			<th><?php echo $this->Paginator->sort('id'); ?></th>
			<th><?php echo $this->Paginator->sort('vendor_id'); ?></th>
			<th><?php echo $this->Paginator->sort('day_name'); ?></th>
			<th><?php echo $this->Paginator->sort('status'); ?></th>
			<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	</thead>
	<tbody>
	<?php foreach ($vendorDays as $vendorDay): ?>
	<tr>
		<td><?php echo h($vendorDay['VendorDay']['id']); ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($vendorDay['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $vendorDay['Vendor']['id'])); ?>
		</td>
		<td><?php echo h($vendorDay['VendorDay']['day_name']); ?>&nbsp;</td>
		<td><?php echo h($vendorDay['VendorDay']['status']); ?>&nbsp;</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View'), array('action' => 'view', $vendorDay['VendorDay']['id'])); ?>
			<?php echo $this->Html->link(__('Edit'), array('action' => 'edit', $vendorDay['VendorDay']['id'])); ?>
			<?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $vendorDay['VendorDay']['id']), array(), __('Are you sure you want to delete # %s?', $vendorDay['VendorDay']['id'])); ?>
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
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('action' => 'add')); ?></li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('controller' => 'meal_menus', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Orders'), array('controller' => 'orders', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Order'), array('controller' => 'orders', 'action' => 'add')); ?> </li>
	</ul>
</div>
