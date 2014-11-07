<div class="vendorReviews index">
	<h2><?php echo __('Vendor Reviews'); ?></h2>
	<table cellpadding="0" cellspacing="0">
	<thead>
	<tr>
			<th><?php echo $this->Paginator->sort('id'); ?></th>
			<th><?php echo $this->Paginator->sort('customer_id'); ?></th>
			<th><?php echo $this->Paginator->sort('vendor_id'); ?></th>
			<th><?php echo $this->Paginator->sort('review'); ?></th>
			<th><?php echo $this->Paginator->sort('ratings'); ?></th>
			<th class="actions"><?php echo __('Actions'); ?></th>
	</tr>
	</thead>
	<tbody>
	<?php foreach ($vendorReviews as $vendorReview): ?>
	<tr>
		<td><?php echo h($vendorReview['VendorReview']['id']); ?>&nbsp;</td>
		<td>
			<?php echo $this->Html->link($vendorReview['Customer']['name'], array('controller' => 'customers', 'action' => 'view', $vendorReview['Customer']['id'])); ?>
		</td>
		<td>
			<?php echo $this->Html->link($vendorReview['Vendor']['name'], array('controller' => 'vendors', 'action' => 'view', $vendorReview['Vendor']['id'])); ?>
		</td>
		<td><?php echo h($vendorReview['VendorReview']['review']); ?>&nbsp;</td>
		<td><?php echo h($vendorReview['VendorReview']['ratings']); ?>&nbsp;</td>
		<td class="actions">
			<?php echo $this->Html->link(__('View'), array('action' => 'view', $vendorReview['VendorReview']['id'])); ?>
			<?php echo $this->Html->link(__('Edit'), array('action' => 'edit', $vendorReview['VendorReview']['id'])); ?>
			<?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $vendorReview['VendorReview']['id']), array(), __('Are you sure you want to delete # %s?', $vendorReview['VendorReview']['id'])); ?>
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
		<li><?php echo $this->Html->link(__('New Vendor Review'), array('action' => 'add')); ?></li>
		<li><?php echo $this->Html->link(__('List Customers'), array('controller' => 'customers', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Customer'), array('controller' => 'customers', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
	</ul>
</div>
